package cn.huaxunchina.cloud.app.model.response;

import java.io.Serializable;
import java.util.List;

import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.Students;
import cn.huaxunchina.cloud.app.model.Teacher;
import cn.huaxunchina.cloud.app.model.UserInfo;

@SuppressWarnings("serial")
public class LoginData implements Serializable{

	public List<Students> students;
	public List<ClassInfo> classList;
	public UserInfo  userinfo;
	public Teacher teacher;
	public List<String> alltags;
	
	
	public List<String> getAlltags() {
		return alltags;
	}
	public void setAlltags(List<String> alltags) {
		this.alltags = alltags;
	}
	public List<Students> getStudents() {
		return students;
	}
	public void setStudents(List<Students> students) {
		this.students = students;
	}
	public List<ClassInfo> getClassList() {
		return classList;
	}
	public void setClassList(List<ClassInfo> classList) {
		this.classList = classList;
	}
	public UserInfo getUserinfo() {
		return userinfo;
	}
	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	
	 
	
}
