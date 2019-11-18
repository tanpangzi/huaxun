package cn.huaxunchina.cloud.app.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.activity.homework.HomeWork;
import cn.huaxunchina.cloud.app.activity.homework.HomeWrokDetail;
import cn.huaxunchina.cloud.app.activity.interaction.HomeSchoolActivities;
import cn.huaxunchina.cloud.app.activity.interaction.InteractionDetail;
import cn.huaxunchina.cloud.app.activity.notice.Notice;
import cn.huaxunchina.cloud.app.activity.notice.NoticeDetail;
import cn.huaxunchina.cloud.app.activity.todayread.TodayRead;
import cn.huaxunchina.cloud.app.activity.todayread.TodayReadDetail1;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.NewsConten;
import cn.huaxunchina.cloud.app.model.NewsInfo;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.R;
/**
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月21日 上午9:00:57 
 *
 */
public class NewsAdapter extends BaseExpandableListAdapter {
	
	private List<NewsInfo> data;
	private Activity activity;
	public NewsAdapter(Activity activity,List<NewsInfo> data){
		this.data=data;
		this.activity=activity;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return data.get(groupPosition).getContentList().get(childPosition);
	}

	@Override
	public long getChildId(int arg0, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		int count = 0;
		if(data!=null){
			count=data.get(groupPosition).getContentList().size();
		}
		return count;
	}
	

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean arg2, View view,ViewGroup arg4) {
		ChildViewHolder viewHolder = null;
		if(view==null){
			view=View.inflate(this.activity, R.layout.news_child_layout, null);
			viewHolder = new ChildViewHolder();
			viewHolder.tvConten=(TextView)view.findViewById(R.id.news_child_content);
			view.setTag(viewHolder);
		}else{
			viewHolder=(ChildViewHolder)view.getTag();
		}
//		viewHolder.tvConten.setTextSize(Utils.px2dip(activity, 26));
		NewsInfo info = data.get(groupPosition);
		final int type = info.getType();
		NewsConten newsConten = info.getContentList().get(childPosition);
		final String id = newsConten.getId();
		viewHolder.tvConten.setText(newsConten.getContent());
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				doActivity(type,false,id);
				
			}
		});
		return view;
	}



	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return data.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		int count = 0;
		if(data!=null){
			count=data.size();
		}
		return count;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}
	@Override
	public View getGroupView(final int groupPosition, boolean arg1, View convertView, ViewGroup arg3) {
		// TODO Auto-generated method stub
		convertView = View.inflate(this.activity, R.layout.news_group_layout, null);
		TextView icon = (TextView)convertView.findViewById(R.id.news_group_icon);
		TextView conten = (TextView)convertView.findViewById(R.id.news_group_titel);
		TextView count = (TextView)convertView.findViewById(R.id.news_group_count);
		NewsInfo info = data.get(groupPosition);
		final int type = info.getType();
		//boolean isnews = info.getIsnew();
		 
		switch (type) {
		case 1:
			icon.setBackgroundResource(R.drawable.news_homeweek_icon);
			conten.setText("家庭作业");
			break;
		case 2:
			icon.setBackgroundResource(R.drawable.news_notice_icon);
			conten.setText("通知资讯");
			break;
		case 3:
			icon.setBackgroundResource(R.drawable.news_read_icon);
			conten.setText("每日一读");
			break;
		case 4:
			icon.setBackgroundResource(R.drawable.news_att_icon);
			conten.setText("家校互动");
			break;
		}
		 //isNews(type)
		if(isNews(type)){
			count.setVisibility(View.VISIBLE);
			//count.setText("new");
		}
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				doActivity(data.get(groupPosition).getType(),true,null);
			}
		});
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	private class ChildViewHolder{
		private TextView tvConten;
	}
	
	//1-家庭作业   2-通知公告  3-每日一读  4-家校互动
	private void doActivity(int type,boolean isTitile,String id){
		//
		String key = getReadKey(type);
		String _id = getReadId(type);
		UserUtil.setNews(key, _id);
		notifyDataSetInvalidated();
		
		
		Intent intent = null;
		if(isTitile){//列表
			switch (type) {
			case 1:
				intent = new Intent(activity, HomeWork.class);
				activity.startActivity(intent);
				break;
			case 2:
				intent = new Intent(activity, Notice.class);
				activity.startActivity(intent);
				break;
			case 3:
				intent = new Intent(activity, TodayRead.class);
				activity.startActivity(intent);
				break;
			case 4:
			intent = new Intent(activity, HomeSchoolActivities.class);
			activity.startActivity(intent);
				break;
			}
		}else{//内容详情
			switch (type) {
			case 1:
				intent = new Intent(activity, HomeWrokDetail.class);
				intent.putExtra("id", id);
				activity.startActivity(intent);
				break;
			case 2:
				intent = new Intent(activity, NoticeDetail.class);
				intent.putExtra("id", id);
				activity.startActivity(intent);
				break;
			case 3:
				intent = new Intent(activity, TodayReadDetail1.class);
				intent.putExtra("isCollect", false);
				intent.putExtra("id", id);
				activity.startActivity(intent);
				break;
			case 4:
				intent = new Intent(activity, InteractionDetail.class);
				intent.putExtra("id", id);
				activity.startActivity(intent);
				break;
			}
		}
	}
	
	/**
	 * TODO(描述)  是否已阅读过
	  * @param type
	  * @return
	 */
	private boolean isNews(int type){
		String key = getReadKey(type);
		String id = getReadId(type);
		boolean b = UserUtil.isNews(key, id);
		return !b;
	}
	
	 
	
	
	 //roles11_2048_home1
	private String getReadKey(int type){
		StringBuffer key = new StringBuffer();
		//获取当前角色
		UserManager user = LoginManager.getInstance().getUserManager();
		int rolesid = Integer.valueOf(user.curRoleId);
		String curId = user.curId;
		if(RolesCode.PARENTS==rolesid){
			key.append("roles"+RolesCode.PARENTS+"_"+curId+"_home"+type);
		}else{
			key.append("roles"+user.curRoleId+"_home"+type);
		}
		
		return key.toString();
	}
	
	private String getReadId(int type){
		StringBuffer id = new StringBuffer();
		int count = data.size();
		List<NewsConten> list = null;
		for(int j=0;j<count;j++){
			if(data.get(j).getType()==type){
				list = data.get(j).getContentList();
			}
		}
		if(list==null)
			return "";
		int size = list.size();
		for(int i=0;i<size;i++){
			id.append("_"+list.get(i).getId());
		}
		return id.toString();
	}
	 
	

}
