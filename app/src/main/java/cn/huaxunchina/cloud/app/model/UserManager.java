package cn.huaxunchina.cloud.app.model;

import java.util.List;

import cn.huaxunchina.cloud.app.model.response.LoginData;
/**
 * 用户本地信息
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月15日 下午3:09:37 
 *
 */
public class UserManager {

	public String curId;// 当前用户的id
	public String curRoleId;// 当前角色
	public String curClassId;//当前班级id学生
	public  String curStudentId;
	public  String curTeacherId;
	public String roleCount;// 角色总数
	public String curRoleName;// 当前角色名称
	public String userId;
	public String userName;// 用户名称
	public String schoolName;// 学校名称
	public String loginPhone;// 账号
	public String password;
	public String clientAppId;//clientId
	public List<Students> students;
	public List<ClassInfo> classInfo;
	public LoginData loginData;
	public int weekTotal;
	public int currentWeek;//当前周
	public List<String> tags;//标签
	

}
