package cn.huaxunchina.cloud.app.adapter;

import cn.huaxunchina.cloud.app.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 家长考勤列表数据Adapter
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月16日 下午5:11:35 
 *
 */
public class AttendancePatriarchAdapter extends BaseAdapter {
	
	private Context context;
	
	public AttendancePatriarchAdapter(Context context){
		this.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(view==null){
			view = View.inflate(context, R.layout.att_patriarch_item,null);
			holder=new ViewHolder();
			/*holder.titile=(TextView) view.findViewById(R.id.profile_header_img);
			holder.type=(ImageView) view.findViewById(R.id.new_mail_icon);*/
			view.setTag(holder);
		}else{
			holder=(ViewHolder) view.getTag();
		}
		
		return view;
	}
	
	
	private class ViewHolder{
		private TextView titile;
		private ImageView type;
	}

}
