package cn.huaxunchina.cloud.app.view;

import java.util.List;

import com.wheel.LocaWheelAdapter;
import com.wheel.WheelView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.model.ServerData.ServerInfo;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
/**
 * 服务器地址列表Dialog
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月7日 下午2:40:04 
 *
 */
public class ServerDialog extends Dialog {
	private ServerCallBack callBack;
	@SuppressWarnings("rawtypes")
	private LocaWheelAdapter server_adapter = null;
	private static int theme = R.style.dialog;// 主题
	private WheelView weekview = null;
	private int width, height;// 对话框宽高
	private List<ServerInfo> data;
	private int position;
	public ServerDialog(Context context,List<ServerInfo> data,int width,int height,int position) {//,int curWeek
		super(context,theme);
		this.width=width;
		this.height=height;
		this.data=data;
		this.position=position;
		int size = data.size();
		String[] weekdata = new String[size];
		for(int i=0;i<size;i++){
			weekdata[i]=data.get(i).getArea();
		}
		server_adapter=new LocaWheelAdapter<String>(weekdata,size);
	}
	
	public ServerDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_url_dialog);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.service_layout);
		LayoutParams lparams_hours = new LayoutParams(width, height / 3 + 10);
		layout.setLayoutParams(lparams_hours);
		weekview=(WheelView)findViewById(R.id.service_index);
		//班级
		weekview.setCyclic(false);
		weekview.setVisibleItems(3);
		weekview.setAdapter(server_adapter);
		weekview.setCurrentItem(position);
		findViewById(R.id.inquiry_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int index = server_adapter.getIndexs();
				ServerInfo info = data.get(index);
				callBack.onServerUrl(info,index);
				dismiss();
				
			}
		});
	}
	
	public void setServerCallBack(ServerCallBack callBack){
		this.callBack=callBack;
	}
	
	public interface ServerCallBack {
		/**
		 * 回调函数，用于在Dialog的监听事件触发后刷新数据
		 */
		public void onServerUrl(ServerInfo info, int index);
	}

}
