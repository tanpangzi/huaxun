package cn.huaxunchina.cloud.app.common;

import android.os.Environment;

/**
 * 常量类
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年6月26日 下午6:00:22
 * 
 */
public final class Constant {

	// app的一些文件路径
	public static String PAXY_PATH = Environment.getExternalStorageDirectory().getPath() + "/paxy/";// 项目文件夹
	public static String IMGCACHE_SAVE_PATH = Environment.getExternalStorageDirectory().getPath() + "/paxy/imgcache/";// 缓存图片路径
	public static String DATA_CACHE_SAVE_PATH = Environment.getExternalStorageDirectory().getPath() + "/paxy/datacache/";// 缓存数据路径
	public static String UPDATE_PATH = Environment.getExternalStorageDirectory().getPath() + "/paxy/update/";// 更新包文件路径
	public static String LOG = Environment.getExternalStorageDirectory().getPath() + "/paxy/log/"; 
	public static String PATH_IMAGE = "/ImageCache/";
	
	public static final String SHARE_IMAGE = Environment.getExternalStorageDirectory().getPath()+"/paxy/share_image.jpg";//分享操作图片路径
	
	public static final String SHAREDPREFERENCES_NAME = "first_pref"; // 本地数据配置标示
	public static final long SPLASH_DELAY_MILLIS = 1000; // 启动界面动画时间
	public static final String CONTENTTYPE = "application/json";//;charset=UTF-8
	
	public static final String LBS_USERNAME ="84d31a856cf50fbeda3c5c1c6c796630"; //测试定位服务模块账号
	public static final String LBS_PASSWORD = "84d31a856cf50fbeda3c5c1c6c796630";  //测试定位服务模块密码
	public static final int LBS_LOGINTYPE = 1;  //测试定位服务模块登录类型
	public static final String  CLIENTAPPID = "";//推送id//13351340001
	public static final String SIM = "";//二代卡手机号码

	/**
	 * 日期
	 */
	public static final String TODAY = "今天";
	public static final String YESTERDAY = "昨天";
	public static final String TOMORROW = "明天";
	public static final String BEFORE_YESTERDAY = "前天";
	public static final String AFTER_TOMORROW = "后天";
	public static final String SUNDAY = "星期日";
	public static final String MONDAY = "星期一";
	public static final String TUESDAY = "星期二";
	public static final String WEDNESDAY = "星期三";
	public static final String THURSDAY = "星期四";
	public static final String FRIDAY = "星期五";
	public static final String SATURDAY = "星期六";

	// webserver url
	public interface Url {
		 String SERVER ="http://app.hxfzsoft.com:10060/schoolyard-root/";//"http://test.hxfzsoft.com:10047/schoolyard-root/";
		 //http://justforcct.meibu.net:10046/schoolyard-root/
		//String LBS = "http://www.cctlbs.com/cmd";
		String LBS = "http://test.hxfzsoft.com:10003/location";
		//"http://172.18.10.35:8080/location/cmd";// "http://test.hxfzsoft.com:10041/location/cmd";
		String MSG_SENDER = "http://www.cctlbs.com/msgSender";
		//String LBS = "http://172.18.10.26:8080/location/cmd";
		//String LBS = "http://172.18.13.51:8080/LBS/cmd";
	}
	
	/**
	 * 
	 *
通知公告    id=86   fun_key=NOTICE_LIST_APP
家庭作业    75   HOMEWORKLIST_APP
考勤记录    92   DAILY_APP
家校互动    88   INTERACT_LIST_APP
请假管理    105  APP_LEAVE_TEACHER
新增请假    106   APP_LEAVE_ADD
成绩管理    103   APP_EXAM_PAGE
每日一读    80   READLIST_APP
	 *
	 */
	
	public interface FunKey {
		String  NOTICE_LIST_APP="NOTICE_LIST_APP";
		String NOTICE_LIST_APP_ID="86";
		String HOMEWORKLIST_APP="HOMEWORKLIST_APP";
		String HOMEWORKLIST_APP_ID="75";
		String DAILY_APP="DAILY_APP";
		String DAILY_APP_ID="92";
		String INTERACT_LIST_APP="INTERACT_LIST_APP";
		String INTERACT_LIST_APP_ID="88";
		String APP_LEAVE_TEACHER="APP_LEAVE_TEACHER";
		String APP_LEAVE_TEACHER_ID="105";
		String APP_EXAM_PAGE="APP_EXAM_PAGE";
		String APP_EXAM_PAGE_ID="103";
		String READLIST_APP="READLIST_APP";
		String READLIST_APP_ID="80";
		
    }
	

	public interface HandlerCode {
		public static final int HANDLER_SUCCESS = 0;// 请求成功
		public static final int HANDLER_FAIL = 1; // 请求失败
		public static final int HANDLER_ERROR = 2; // 请求错误
		public static final int HANDLER_NET = 3; // 请求无网络
		public static final int FORGET_VERIFYCODE = 4;
		public static final int FORGET_SUCCESS = 5;
		public static final int HANDLER_START = 6;// 开始
		public static final int HANDLER_TIME_OUT = 7;// 超时
		public static final int SMSCONTACTS_TXT = 8;//
		public static final int HANDLER_LASTPAGE = 9;//list最后一页
		public static final int NOTICE_ONREFRESH_COMPLETE = 10;//加载最后一页，加载完成
		public static final int BACK_DATA_ERROR = 11;//接口返回异常
		public static final int SERVER_SUCCESS = 12;// 请求成功
		
		public static final int VERSION_SUCCESS = 13;
		public static final int VERSION_ERROR = 14;
		public static final int DEL_SUCCESS = 15;// 删除成功
		public static final int DEL_FAIL = 16;// 删除失败
		public static final int GROUP_CONTACTS_TXT = 17;//
		public static final int HOMEWORKCK_TXT = 18;//
		
		public static final int HANDLER_PIC = 20;// 拍照
		public static final int HANDLER_GET_PHOTO = 21;// 取图册照片
		
		public static final int COLLECT_SUCCESS = 100;//收藏成功
		public static final int DEL_COLLECT_SUCCESS = 101;//取消成功
		public static final int SUMBIT_HOME_SCHOOL_COMMENT_SUCCESS = 102;//发布家校互动详情评论成功
		public static final int GET_HOMEWROK_CLASS_COURSE_SUCCESS = 200;//获取家庭作业班级科目成功
		public static final int GET_HOME_SHCOOL_LIST_NO_DATA = 205;//获取家校互动列表暂无数据
		public static final int SUMBIT_HOME_SCHOOL_QUESTIONS_SUCCESS = 240;//发布家校互动成功
		
		public static final int COOKIESTORE_SUCCESS = 401;//cook失效
		public static final int LBS_902 = 902;//无效
		public static final int LBS_901 = 901;//禁用 
		
	}

	public interface RolesCode {

		public static final int SCH_MANAGER = 6;// 学校管理员
		public static final int SCH_HELP_MANAGER = 7;// 副管理员
		public static final int GRADE_LEADER = 8;// 组长
		public static final int HEAD_TEACHER = 9;// 班主任
		public static final int TEACHER = 10;// 任课老师
		public static final int PARENTS = 11;// 家长

	}
	
	
	public interface ResultCode{
		public static final int SMSCONTENT_CODE = 1;
		public static final int PROFILE_CODE = 2;
		public static final int LOGIN_CODE = 3;
		public static final int READ_DETAIL = 4;
		public static final int READ_COLLECT = 5;
		public static final int REFRESH_CHECK_STATE = 6;
		public static final int LOCUS_CODE = 7;
		public static final int FEN_CODE = 8;
		public static final int FEN_DEL_CODE= 9;
		public static final int SMSGROUP_CODE= 10;
		public static final int SMSSEND_CODE= 11;
		public static final int ATT_CODE= 12;
		public static final int MORE_DATA_CODE = 13;
		
	}

	public interface CourseCode {// 科目
		public static final int YUWEN = 1;// 语文
		public static final int SHUXUE = 2;// 数学
		public static final int YINGYU = 3;// 英语
		public static final int ZHENGZHI = 4;// 政治
		public static final int WULI = 5;// 物理
		public static final int HUAXUE = 6;// 化学
	}
	
	// webserver 相应接口
	public interface RequestCode {
		String SERVER_LIST=Url.SERVER+"serverList.do";
		String CLIENT_INFO = Url.SERVER+"clientInfo.do";
		String DOWNLOAD_CLIENT = Url.SERVER+"downloadClient.do"; //下载更新客户端URL

		String AJAXDOLOGIN = "/ajaxDologin.do?";// 登陆//ajaxDologin.do?
		String AJAXCHANGEROLE = "/ajaxChangeRole.do?";// 角色切换
		String LOGOUT = "/logout.do?";// 注销
		String AJAXCHANGEPWD = "/user/ajaxChangePwd.do?";// 修改密码
		String USER_AJAXFORGET = "/user/ajaxCreateSmsCode.do?";// 忘记密码-验证
		String USER_AJAXGETNEWPASSWD = "/user/ajaxGetNewPasswd.do?";// 忘记密码-设置密码
		String NOTICE_AJAXNOTICELIST = "/notice/ajaxNoticeList.do?";// 通知公告列表
		String NOTICE_AJAXGETNOTICE = "/notice/ajaxGetNotice.do?";// 通知公告详情
		String NOTICE_AJAXADDNOTICE = "/notice/ajaxAddNotice.do?";// 新增通知公告
		String COURSE_QUERYCOURSEBYWEEKOFYEAR = "/course/queryCourseByWeekofyear.do?";// 课程表
		String DOAPPSELECT ="/course/course/doAppSelect.do?";//课程表新接口
		//String COURSE_AJAXSTUDENTBYCLAZZ = "scorenew/queryExaminationOfAllClazzSqlPage.do?";// 成绩管理
		String LISTEXAMINATION ="/score/listexamination.do?";// 成绩管理
		String COURSE_SCOREDETAIL = "/scorenew/getExamScoreDetailOfAllClazz_Json.do?";// 考试详情
		String COURSE_AJAXEXAMLIST = "/course/ajaxExamList.do?";// 考试列表
		String COURSE_AJAXCLASSSCORES = "/course/ajaxClassScores.do?";// 班级考试单科成绩表
		String FIVE_SCORES = "/studentscore/queryFiveExamListByCurrentTime_Json.do?";//五次成绩
		String AJAXADDASKLEAVE = "/leave/mobileAddLeaveByStudentid.do?";// 请假申请
		String LEAVEBYID = "/leave/getLeaveById.do?";//请假详情leaveId=1
		String ASKLEAVE_AJAXCHECKASKLEAVE = "/leave/submitApproveLeaveForStudent.do?";// 请假审核
		String READING_AJAXREADLIST = "/interaction/queryReadevery.do?";// 每日一读列表
		String READING_AJAXADDFAV = "/interaction/queryColletlist.do?";// 阅读收藏夹列表
		String COLLECT_TODAY_READ = "/interaction/addCollect.do?"; // 收藏每日一读文章
		String DEL_TODAY_READ = "/interaction/deleteColletlist.do?"; // 取消收藏每日一读文章
		String READING_AJAXREADDETAIL = "/interaction/toReadDataBrowseApp.do?";// 每日一读 详情
		String HOMEWORK_AJAXHOMEWORKLIST = "/course/listHomework.do?";// 家庭作业列表
		String DEL_HOMEWORK = "/course/deleteHomework.do?";// 删除家庭作业
		String HOMEWORK_COURSENAME = "/course/addHomeworkInit.do?";// 获取家庭作业科目
		String SUMBIT_HOMEWORK = "/course/addHomework.do?";// 发布家庭作业
		String HOMEWORK_DETAIL = "/course/selectHomeworkByIdApp.do?";// 获取家庭作业详情
		String ASKLEAVE_AJAXGETASKLEAVE = "/askleave/ajaxGetAskLeave.do?";// 请假详情
		String ASKLEAVE_AJAXASKLEAVELIST = "/leave/getLeaveAllByTeacherid_Json.do?";// 请假列表(孙志坚请假列表接口)
		String ATTENDANCE_AJAXGETATTENDANCE = "/attendance/ajaxGetAttendance.do?";// 考勤记录
		String READING_AJAXLASTTHEMES = "/interaction/ajaxLastThemes.do?";// 家校互动 列表
		String READING_AJAXGETTHEME = "/interaction/ajaxViewThemeDetail.do?";// 家校互动 详情
		String READING_AJAXADDTHEME = "/interaction/ajaxAddTheme.do?";// 在线提问
		String READING_COMMENTLIST = "/interaction/ajaxAddComment.do?";// 发表家校互动评论列表
		String READING_TEACHERANDSUBJECT = "/interaction/ajaxGetTeacherSubject.do?";// 获取家校互动老师与科目
		String HOMEWORK_AJAXGETHOMEWORK = "/homework/ajaxGetHomework.do?";// 家庭作业
		String HOMEWORK_AJAXADDHOMEWORK = "/homework/ajaxAddHomework.do?";// 发布家庭作业
		String CONTACTS= "/interaction/customGroupApp.do?";//通讯录
		String SENDSMS = "/interaction/saveSMSApp.do?";//短信发送
		String HOSSMS = "/interaction/NewSMSListByUserId.do?";//历史短信
		String HOSSMSDETAIL="/interaction/getSMSbyIdNew.do?";//历史短信详情
		String WITHROLE="/ajaxDologinWithRole.do?";//角色 密码 账号直接登陆
		String NEWS = "/ajaxFirstPageList.do?";//首页
		String FEEDBACK = "/feedback.do?";//反馈
		String UPDATECLIENTAPP ="user/ajaxUpdateClientApp.do?";//更新clientId
		String LOC_MESSAGELIST = "/statistic/alarmList.do?";// 定位模块消息列表请求接口

	}
}


