package cn.huaxunchina.cloud.app.view;

import java.util.List;

import com.wheel.LocaWheelAdapter;
import com.wheel.WheelView;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.StringUtil;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 课程表查询Dialog TODO(描述)
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月7日 下午2:40:04
 * 
 */
public class SyllabusDialog extends Dialog {

	private SyllabusCallBack callBack;
	private LocaWheelAdapter class_adapter = null;
	private static int theme = R.style.dialog;// 主题
	private WheelView classview = null;
	private int width, height;// 对话框宽高
	private Context context;
	private String curRoleId = "";
	private List<ClassInfo> classlist;

	public SyllabusDialog(Context context, int width, int height) {// ,int
																				// curWeek
		super(context, theme);
		this.context = context;
		this.width = width;
		this.height = height;
		UserManager umanager = LoginManager.getInstance().getUserManager();
		// ========多少周
		curRoleId = umanager.curRoleId;
		// =======班主任需要选择班级
		if (curRoleId.equals(String.valueOf(RolesCode.HEAD_TEACHER))) {
			this.classlist = umanager.classInfo;
			int class_size = classlist.size();
			String[] classdata = new String[class_size];
			for (int i = 0; i < class_size; i++) {
				classdata[i] = StringUtil.substring(classlist.get(i).getClassName(), 10);
			}
			class_adapter = new LocaWheelAdapter<String>(classdata, class_size);
		}
	}

	public SyllabusDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.syllabus_dialog);

		LinearLayout layout = (LinearLayout) findViewById(R.id.syllabus_layout);
		LayoutParams lparams_hours = new LayoutParams(width, height / 3 + 10);
		layout.setLayoutParams(lparams_hours);

		classview = (WheelView) findViewById(R.id.syllabus_class_index);
		// 班级
		if (curRoleId.equals(String.valueOf(RolesCode.HEAD_TEACHER))) {
			classview.setVisibility(View.VISIBLE);
			classview.setCyclic(false);
			classview.setVisibleItems(5);
			classview.setAdapter(class_adapter);
			classview.setCurrentItem(0);
		}
		 
		findViewById(R.id.syllabus_inquiry_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						int classIndexs = class_adapter.getIndexs();
						String classId = classlist.get(classIndexs).getClassId();
						callBack.onSelectdata(classId);
						dismiss();

					}
				});
	}

	public void setSyllabusCallBack(SyllabusCallBack callBack) {
		this.callBack = callBack;
	}

	public interface SyllabusCallBack {
		/**
		 * 回调函数，用于在Dialog的监听事件触发后刷新数据
		 */
		public void onSelectdata(String classindex);
	}

}
