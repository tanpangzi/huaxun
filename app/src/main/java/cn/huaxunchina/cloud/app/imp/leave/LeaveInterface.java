package cn.huaxunchina.cloud.app.imp.leave;

import com.loopj.android.http.AsyncHttpClient;

import android.app.Activity;
import android.os.Handler;

/**
 * 请假管理接口列表实现类
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-8-15 下午4:23:16
 */
public interface LeaveInterface {
	void getLeaveList(Activity activity, AsyncHttpClient httpUtils, Handler handler, String studentId, String teacherId, String roleflag, String sta, String limit);// 获取请假管理列表信息
	void SumbitLeave(Activity activity, AsyncHttpClient httpUtils, Handler handler, String leaveObject, String leaveType, String leaveContent, String staDate, String endDate, String studentId, String hour);// 提交请假申请信息
	void getCheckState(Activity activity, AsyncHttpClient httpUtils, Handler handler, String askLeaveId, String sendStatus);// 提交请假审核状态
	void getgetLeaveInfo(Activity activity, AsyncHttpClient httpUtils, Handler handler, String id);//请假详情
}
