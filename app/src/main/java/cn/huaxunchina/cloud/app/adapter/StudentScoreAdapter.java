package cn.huaxunchina.cloud.app.adapter;


import java.util.List;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.StudentScore;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 学生的考试详情 adapter
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月12日 下午7:14:42 
 *
 */
public class StudentScoreAdapter extends BaseAdapter {

	private List<StudentScore> data;
	private String curRoleId = null;
	private String commentt = "";
	private String eTime = "";
	private String eName = "";

	public StudentScoreAdapter(List<StudentScore> data){
	this.data=data;
	UserManager manager = LoginManager.getInstance().getUserManager();
	curRoleId=manager.curRoleId;
	
	if(data!=null&&data.size()>0){
		StudentScore score = data.get(0);
		this.seteName(score.getExamTypeName());
		this.seteTime(score.getExamTime());
	}
	
	}
	
	
	@Override
	public int getCount() {
		int count = 0;
		if(data!=null){
		count = data.size();	
		
		}
		return count;
	}
	
	public String getCommentt(){
		return commentt;
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
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(view==null){
			view = View.inflate(ApplicationController.getContext(), R.layout.student_score_item,null);
			holder=new ViewHolder();
			holder.tvStudentName=(TextView) view.findViewById(R.id.tv_student_name);
			holder.tvCourse=(TextView) view.findViewById(R.id.tv_score);
			view.setTag(holder);
		}else{
			holder=(ViewHolder) view.getTag();
		}
		StudentScore score = data.get(itemId);
		if(curRoleId.equals(String.valueOf(RolesCode.TEACHER))){//任课老师显示学生名字
			holder.tvStudentName.setText(score.getStudentName());	
		}else{
			holder.tvStudentName.setText(score.getCourseName());
		}
		holder.tvCourse.setText(score.getScore());
		commentt=score.getCommentt();
		return view;
	}
	
	 
	
	private class ViewHolder{
		private TextView tvStudentName;
		private TextView tvCourse;
	}
	
	public String geteTime() {
		return eTime;
	}


	public void seteTime(String eTime) {
		if(eTime!=null){
		this.eTime = eTime;
		}
	}


	public String geteName() {
		return eName;
	}


	public void seteName(String eName) {
		if(eName!=null){
		this.eName = eName;
		}
	}


}
