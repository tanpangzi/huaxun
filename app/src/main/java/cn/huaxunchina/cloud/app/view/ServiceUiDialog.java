package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/**
 * 
 * TODO(描述) 全局Dialog
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月14日 下午12:18:20 
 *
 */
public class ServiceUiDialog extends Dialog {
	
	private Context context;
	private String categoryId;
	private static int theme = R.style.dialog;// 主题
	private TextView tvConet;
	private String conet="";
	
	public ServiceUiDialog(Context context,String categoryId,String msgContent) {
		super(context,theme);
		this.context=context;
		this.categoryId=categoryId;
		//categoryId 1=通知公告  2=考勤  3=成绩 4=家校互动 5=家庭作业 6=请假 7=无栏目对应类型消息
		conet=msgContent;
	}

	public ServiceUiDialog(Context context, int theme) {
		super(context, theme);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_dialog);
		tvConet=(TextView)findViewById(R.id.conet);
		tvConet.setText(conet);
		findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		findViewById(R.id.look_up).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				if(lookActivity!=null){
				lookActivity.onClickLookBtn();
				}
			}
		});
		 
	}
	
	private LookActivity lookActivity;
	
	public void setLookActivity(LookActivity lookActivity){
		this.lookActivity=lookActivity;
	}
	
	public interface LookActivity{
		
		public abstract void onClickLookBtn();
	}

}
