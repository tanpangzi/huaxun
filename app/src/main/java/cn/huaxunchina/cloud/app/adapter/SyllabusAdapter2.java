package cn.huaxunchina.cloud.app.adapter;

import java.util.List;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.Syllabus1;
import cn.huaxunchina.cloud.app.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 *课程表Adapter
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月16日 下午5:11:35 
 *
 */
public class SyllabusAdapter2 extends BaseAdapter {
	
	
	private List<Syllabus1> data;
	private Context context;
	private int curRoleId;
	public SyllabusAdapter2(List<Syllabus1> data){
		this.data=data;
		this.context=ApplicationController.getContext();
		curRoleId=Integer.valueOf(LoginManager.getInstance().getUserManager().curRoleId);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return listdata.size();
		return data.size();
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
	public View getView(int itemId, View view, ViewGroup arg2) {
		//第1节课-语文      小学一年级(老师)
		//1 数学                 王语雨(家长)
		//1 语文                 王语雨(班主任)
		ViewHolder holder=null;
		if(view==null){
		holder = new ViewHolder();
		view = View.inflate(context, R.layout.syllabus_courseitem_layout, null);
		holder.courseId = (TextView)view.findViewById(R.id.course_id);
		holder.courseName = (TextView)view.findViewById(R.id.course_name);
		holder.courseTeacher = (TextView)view.findViewById(R.id.course_teacher);
		view.setTag(holder);
		}else{
		holder=(ViewHolder) view.getTag();
		}
		Syllabus1 info = data.get(itemId);
	    switch (curRoleId) {
		case RolesCode.PARENTS://家长
		case RolesCode.HEAD_TEACHER://班主任
			holder.courseId.setText((itemId+1)+"");
			holder.courseName.setText(info.getCourseName());
			holder.courseTeacher.setText(info.getTearcherName());
			break;
		case RolesCode.TEACHER://任课老师
			holder.courseId.setVisibility(View.GONE);
			holder.courseName.setText(info.getName()+"-"+info.getCourseName());
			holder.courseTeacher.setText(info.getClassesName());
			break;
		}
		return view;
	}
	
	
	private static class ViewHolder{
		private TextView courseId;
		private TextView courseName;
		private TextView courseTeacher;
	}
	
 
	

}
