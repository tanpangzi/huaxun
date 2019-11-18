package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;


public class Role implements Serializable {

	private int roleId;
	private String roleName;
	private List<Function> functions;
	
	 
	public List<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
