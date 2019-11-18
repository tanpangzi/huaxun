package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.adapter.AttPatriarchAdapter;
import cn.huaxunchina.cloud.app.getui.GetuiUtil;
import cn.huaxunchina.cloud.app.model.AttendanceInfo;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
/**
 * 
 * TODO(描述) 全局Dialog
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月14日 下午12:18:20 
 *
 */
public class ServiceDialog extends Dialog {
	
	private Context context;
	private String categoryId;
	private static int theme = R.style.dialog;// 主题
	
	public ServiceDialog(Context context,String categoryId) {
		super(context,theme);
		this.context=context;
		this.categoryId=categoryId;
	}

	public ServiceDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_dialog);
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
