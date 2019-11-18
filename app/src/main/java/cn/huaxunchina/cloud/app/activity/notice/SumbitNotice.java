package cn.huaxunchina.cloud.app.activity.notice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.notice.NoticeImpl;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.model.notice.NoticeData;
import cn.huaxunchina.cloud.app.tools.ActivityForResultUtil;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.app.view.MyTextWatcher;
import cn.huaxunchina.cloud.app.view.PopMenu;
import cn.huaxunchina.cloud.app.view.SumbitNoiceDialog;
import cn.huaxunchina.cloud.app.view.SumbitNoiceDialog.SumbitNoiceCallBack;

/**
 * 发布公告界面
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-7-22 下午4:39:39
 */
public class SumbitNotice extends BaseActivity implements OnClickListener,SumbitNoiceCallBack {
	private TextView sumbit_text, ed_text, sumbit_object_txt;
	private ImageView jt_image;
	private EditText notice_edit_title, notice_edit_content;
	private RelativeLayout image_relative;
	private MyBackView back;
	private PopMenu popMenu = null;
	private String mImageFileName;
	private ImageView image_photo;
	private UserManager manager;
	private String title, content, class_id, class_name;
	private String[] classId, className;
	private int width, height;
	private Window window;
	private SumbitNoiceDialog dialog;
	private RelativeLayout sumbit_notice_realtive;
	private Intent intent;
	private boolean visible;
	private File fileImage = null;
	private int outputX = 600;
	private int outputY = 600;
	private int aspectX = 1;
	private boolean return_data = false;
	private int aspectY = 1;
	private boolean scale = true;
	private boolean faceDetection = true;
	private boolean circleCrop = false;
	private int select_class=0;
	private final String NOTICE_IMAGE_PATH = Constant.IMGCACHE_SAVE_PATH+ "notice_image";// 公告图片存放路径
	@SuppressLint("SdCardPath")
	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";
	private Uri imageUri;//to store the big bitmap

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sumbit_notice);
		findView();
		initView();
		bindView();
	}

	@Override
	public void findView() {
		back = (MyBackView) findViewById(R.id.back);
		sumbit_text = (TextView) findViewById(R.id.sumbit_text);
		notice_edit_title = (EditText) findViewById(R.id.notice_edit_title);
		notice_edit_content = (EditText) findViewById(R.id.notice_edit_content);
		ed_text = (TextView) findViewById(R.id.ed_text);
		sumbit_object_txt = (TextView) findViewById(R.id.sumbit_object);
		image_relative = (RelativeLayout) findViewById(R.id.relative_image);
		image_photo = (ImageView) findViewById(R.id.image_photo);
		sumbit_notice_realtive = (RelativeLayout) findViewById(R.id.sumbit_notice_realtive);
		jt_image=(ImageView) findViewById(R.id.jt);
		super.findView();
	}

	@Override
	public void initView() {
		back.setBackText("发布通知");
		back.setAddActivty(this);
		imageUri = Uri.parse(IMAGE_FILE_LOCATION);
		noticeImpl = new NoticeImpl();
		loadingDialog = new LoadingDialog(context);
		manager = LoginManager.getInstance().getUserManager();
		List<ClassInfo> infos = manager.classInfo;
		if (infos != null && infos.size() > 0) {
			classId = new String[infos.size()];
			className = new String[infos.size()];
			for (int i = 0; i < infos.size(); i++) {
				ClassInfo c_info = infos.get(i);
				className[i] = c_info.getClassName();
				classId[i] = c_info.getClassId();
			}
			if(className.length>1){
				visible = true;
				jt_image.setVisibility(View.VISIBLE);
			}else{
				sumbit_object_txt.setText(className[0]);
				class_id=classId[0];
			}
			
		}else{
			sumbit_object_txt.setText("暂无可发布班级");
		}
		MyTextWatcher myTextWatcher = new MyTextWatcher(notice_edit_content,ed_text, 150);
		notice_edit_content.addTextChangedListener(myTextWatcher);
		notice_edit_content.setSelection(notice_edit_content.length()); // 将光标移动最后一个字符后面
		myTextWatcher.setLeftCount();
		super.initView();
	}

	@Override
	public void bindView() {
		sumbit_text.setOnClickListener(this);
		image_relative.setOnClickListener(this);
		sumbit_notice_realtive.setOnClickListener(this);
		image_photo.setOnClickListener(this);
		super.bindView();
	}

	@SuppressLint({ "ShowToast", "NewApi" })
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.sumbit_notice_realtive: // 选择发布对象
			if (visible) {
				getScreen();
				dialog = new SumbitNoiceDialog(context, width, height,className,select_class);
				getDialogView(dialog);
				dialog.setSumbitNoiceCallBack(this);
				dialog.show();
			}
			break;
		case R.id.sumbit_text: // 发布公告
			title = notice_edit_title.getText().toString().trim();
			content = notice_edit_content.getText().toString().trim();
			SumbitNoticeMessage();
			break;
		case R.id.relative_image: // 图片拍照上传
			popMenu = new PopMenu(context, R.layout.choice_image_pop_menu, 0);
			View view = popMenu.getView();
			view.findViewById(R.id.btn_pic).setOnClickListener(this);
			view.findViewById(R.id.btn_select).setOnClickListener(this);
			view.findViewById(R.id.btn_cancel).setOnClickListener(this);
			popMenu.showAtLocationBottom(v);
			break;
		case R.id.btn_pic:
			getSDcardPathFile();
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
			startActivityForResult(intent,ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CAMERA);
			popMenu.dismiss();
			break;
		case R.id.btn_select:
			photoZoom(imageUri);
			popMenu.dismiss();
			break;
		case R.id.btn_cancel:
			popMenu.dismiss();
			break;
		case R.id.image_photo:
			new AlertDialog.Builder(context)
					.setMessage("删除该张选取的图片")
					.setPositiveButton("删除",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									image_photo.setVisibility(View.GONE);
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();

			break;
		default:
			break;
		}

	}

	/**
	 * 获取照片路径
	 */

	public void getSDcardPathFile() {
		String filePath = NOTICE_IMAGE_PATH + "/" + manager.curId + "/";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		mImageFileName = filePath
				+ DateUtil.formatDateAndTime(new Date(), "yyyyMMddHHmmss")
				+ ".png";
		fileImage = new File(mImageFileName);
		if (fileImage.exists()) {
			fileImage.delete();
		}

	}

	/**
	 * 获取屏幕适配
	 */
	public void getScreen() {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;
	}

	/**
	 * 获取底部对话框视图
	 */
	public void getDialogView(Dialog dialog) {
		window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置 //Gravity.BOTTOM
		window.setWindowAnimations(R.style.animation_dialogstyle); // 添加动画
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
	}

	@Override
	public void onSelectdata(int classindex) {
		select_class=classindex;
		class_id = classId[classindex];
		class_name = className[classindex];
		sumbit_object_txt.setText(class_name);

	}

	String _content = "";
	/**
	 * 公告发布
	 */
	@SuppressLint("ShowToast")
	private void SumbitNoticeMessage() {
		// 无网络请求
		if (!Utils.isNetworkConn()) {
			Utils.netWorkMessage(handler);
			return;
		}
		if (title.equals("") || content.equals("")) {
			showToast("请输入通知主题或内容");
			return;
		}
		if (sumbit_object_txt.getText().toString().equals("请选择发布对象")) {
			showToast("请选择发布的班级");
			return;
		}
		loadingDialog.show();
		_content=content;
		content = content.replaceAll("\n", "<br>");
		//System.out.println(content);
		noticeImpl.SumbitNoticeMessage(SumbitNotice.this,httpUtils, handler, content, title, "4",class_id, mImageFileName);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressLint("ShowToast")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				String noticeId = (String) msg.obj;
				loadingDialog.dismiss();
				showToast("发布成功");
				NoticeData noticeData = storeLocalData(noticeId);
				Intent intent = new Intent();
				intent.putExtra("storeData", noticeData);
				intent.setAction("action.refreshNoticeList");
				sendBroadcast(intent); // 发送及时刷新广播
				finish();
				break;
			case HandlerCode.HANDLER_NET:
				Utils.netWorkToast(); // 请求网络异常
				break;
			case HandlerCode.HANDLER_ERROR:// 失败
				loadingDialog.dismiss();
				showToast("请求失败");
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				loadingDialog.dismiss();
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				showLoginDialog(SumbitNotice.this);
			    break;
			}

		};

	};

	/**
	 * 发布公告成功后保存本地数据
	 */
	public NoticeData storeLocalData(String noticeId) {
		NoticeData noticeData = new NoticeData();
		noticeData.setContent(_content);
		noticeData.setPublisher(manager.userName);
		noticeData.setPublishTime(DateUtil.getCurrentTime());
		noticeData.setTitle(title);
		noticeData.setNoticeId(noticeId);
		noticeData.setImgList(null);
		return noticeData;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			showToast("取消上传");
			return;
		} else {
			switch (requestCode) {
			case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CAMERA: // 手机拍照后获取的照片
				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					showToast("SD不可用");
					return;
				}
				startPhotoZoom(imageUri);
				break;
			case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_LOCATION: // 本地相册获取的图片
				if (data == null) {
					showToast("取消上传");
					return;
				}
				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					showToast("SD不可用");
					return;
				}
				
				if (imageUri!= null) {
					Bitmap bitmap = decodeUriAsBitmap(imageUri);
					saveCropPhoto(bitmap);
				} else {
					showToast("获取裁剪照片错误");
				}
				break;

			case ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CROP: // 裁剪照片
				if (data == null) {
					showToast("取消裁剪图片");
					return;
				} else {
					if (imageUri!= null) {
						Bitmap bitmap = decodeUriAsBitmap(imageUri);
						saveCropPhoto(bitmap);
					} else {
						showToast("获取裁剪照片错误");
					}
				}
				break;
			default:

				break;

			}
			super.onActivityResult(requestCode, resultCode, data);

		}
	}

	/**
	 * 设置图片展示动画效果
	 */

	public void setAnimation() {
		Animation myAnimation = AnimationUtils.loadAnimation(context,R.anim.image_translate);
		image_photo.startAnimation(myAnimation);
	}

	
	/**
	 * 选择图片后裁剪
	 * 
	 */
	
	public void photoZoom(Uri uri){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		//裁剪框比例
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		//不启用人脸识别
		intent.putExtra("noFaceDetection", false); 
		startActivityForResult(intent, ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_LOCATION);

	}
	/**
	 * 系统裁剪照片
	 * 
	 * @param uri
	 */
	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX); // aspectX aspectY 是宽高的比例
		intent.putExtra("aspectY", aspectY);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", scale);
		intent.putExtra("return-data", return_data);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", faceDetection);
		if (circleCrop) {
			intent.putExtra("circleCrop", true);
		}
		startActivityForResult(intent,ActivityForResultUtil.REQUESTCODE_UPLOADAVATAR_CROP);
	}

	/**
	 * 保存裁剪的照片
	 * 
	 * @param data
	 */
	private void saveCropPhoto(Bitmap bitmap) {
		image_photo.setVisibility(View.VISIBLE);
		image_photo.setImageBitmap(bitmap);
		SaveBitmap(bitmap);
		setAnimation();
	}

	/**
	 * decode bitmap
	 * 
	 * @param uri
	 * @return
	 */
	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	/**
	 * 获取的图片插入到指定文件路径下
	 */

	public void SaveBitmap(Bitmap Bmp) {
		FileOutputStream out = null;
		try {
			getSDcardPathFile();
			out = new FileOutputStream(fileImage);
			if (out != null) {
				Bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
				try {
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
