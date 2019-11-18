package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Students implements Serializable {

	private String classId;//班级id
	private String name;//学生名字
	private String parentsId;
	private String relation;
	private String studentId;//学生id
	private String studentNo;
	private String imeiNum;//设备号
	private boolean bindCard;//卡类型
	private int cardType;//1.一代卡 2.二代卡
	
	
	
	public int getCardType() {
		return cardType;
	}
	public void setCardType(int cardType) {
		this.cardType = cardType;
	}
	public boolean isBindCard() {
		return bindCard;
	}
	public void setBindCard(boolean bindCard) {
		this.bindCard = bindCard;
	}
	public String getImeiNum() {
		return imeiNum;
	}
	public void setImeiNum(String imeiNum) {
		this.imeiNum = imeiNum;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentsId() {
		return parentsId;
	}
	public void setParentsId(String parentsId) {
		this.parentsId = parentsId;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	
	
}
