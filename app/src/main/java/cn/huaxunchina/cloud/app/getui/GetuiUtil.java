package cn.huaxunchina.cloud.app.getui;

import cn.huaxunchina.cloud.app.activity.attendance.AttendanceList;
import cn.huaxunchina.cloud.app.activity.homework.HomeWork;
import cn.huaxunchina.cloud.app.activity.homework.HomeWrokDetail;
import cn.huaxunchina.cloud.app.activity.interaction.HomeSchoolActivities;
import cn.huaxunchina.cloud.app.activity.interaction.InteractionDetail;
import cn.huaxunchina.cloud.app.activity.leave.LeaveDetail1;
import cn.huaxunchina.cloud.app.activity.leave.LeaveManage;
import cn.huaxunchina.cloud.app.activity.notice.Notice;
import cn.huaxunchina.cloud.app.activity.notice.NoticeDetail;
import cn.huaxunchina.cloud.app.activity.score.ScoreDetail;
import cn.huaxunchina.cloud.app.activity.score.ScoreDetailFragment;
import cn.huaxunchina.cloud.app.activity.score.ScoreList;
import cn.huaxunchina.cloud.location.app.activity.msg.LocMessageDetail1;
import android.content.Context;
import android.content.Intent;
/**
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月22日 下午3:55:11 
 *
 */
public class GetuiUtil {
	
	private static GetuiUtil getui = null;
	
	private GetuiUtil(){
	}
	
	public static GetuiUtil getInstance(){
		if(getui==null){
			getui=new GetuiUtil();
		}
		return getui;
	}
	
	
	private GetuidoActivity getuidoActivity;
	
	public void setGetuidoActivity(GetuidoActivity getuidoActivity){
		this.getuidoActivity=getuidoActivity;
	}
	
	public  void categoryType(String categoryId,String id,Context context){
		//categoryId 1=通知公告  2=考勤  3=成绩 4=家校互动 5=家庭作业 6=请假 7=无栏目对应类型消息
		System.out.println("GetuiUtil_1 家庭作业:id======"+id);
		if(categoryId!=null){
			Intent intent = new Intent();
			if (categoryId.equals("1")) {
				intent.putExtra("id", id);
				intent.setClass(context, NoticeDetail.class);
				getuidoActivity.doActivity(intent);
			}else if(categoryId.equals("2")){
				intent.setClass(context, AttendanceList.class);
				getuidoActivity.doActivity(intent);
			}else if(categoryId.equals("3")){
				intent.putExtra("examId", id);
				intent.putExtra("examName", "");
				intent.putExtra("examTime", "");
				intent.setClass(context, ScoreDetail.class);//家长
				getuidoActivity.doActivity(intent);
			}else if(categoryId.equals("4")){
				intent.putExtra("id", id);
				intent.setClass(context, InteractionDetail.class);
				getuidoActivity.doActivity(intent);
			}else if(categoryId.equals("5")){
				System.out.println("GetuiUtil_2 家庭作业:id======"+id);
				intent.putExtra("id", id);
				intent.setClass(context, HomeWrokDetail.class);
				getuidoActivity.doActivity(intent);
			}else if(categoryId.equals("6")){
				intent.putExtra("id", id);
				intent.setClass(context, LeaveDetail1.class);
				getuidoActivity.doActivity(intent);
			}else if(categoryId.equals("100")){//lbs消息详情
				intent.putExtra("id", id);
				intent.setClass(context, LocMessageDetail1.class);
				getuidoActivity.doActivity(intent);
				 
			}
		}
	}

	public interface GetuidoActivity{
		public abstract void doActivity(Intent intent);
	}
}
