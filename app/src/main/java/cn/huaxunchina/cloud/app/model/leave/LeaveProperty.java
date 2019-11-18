package cn.huaxunchina.cloud.app.model.leave;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("serial")
public class LeaveProperty implements Serializable {
	private String studentName; // 请假人
	private int leaveType; // 请假类型
	private String askTime; // 申请时间
	private String approveStatus;// 审批状态
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	private String reason; // 请假原因
	private String askLeaveId;//请假id
	
	

	public String getReason() {
		return "原因："+reason;
	}

	public String getAskLeaveId() {
		return askLeaveId;
	}

	public void setAskLeaveId(String askLeaveId) {
		this.askLeaveId = askLeaveId;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStudentName() {
		return studentName;
	}
	

	

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	@Override
	public String toString() {
		return "LeaveProperty [studentName=" + studentName + ", leaveType="
				+ leaveType + ", askTime=" + askTime + ", approveStatus="
				+ approveStatus + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}

	public int getLeaveType() {
		return leaveType;
	}
	
	public String getLeaveTypeStr(){
		String type[] = {"其他", "事假", "病假"};
		return type[leaveType];
		
	}

	public void setLeaveType(int leaveType) {
		this.leaveType = leaveType;
	}

	public String getAskTime() {
		return askTime;
	}

	public String getApproveStatus() {
		return approveStatus;
	}
	
	public String getApproveStatusStr(){
		
		if(approveStatus.equals("W")){
			return "未审批";
		}
		if(approveStatus.equals("Y")){
			return "同意";
		}
		if(approveStatus.equals("N")){
			return "退回";
		}
		return "";
	}
	
	public boolean isStatus(){
		boolean b = false;
		if(approveStatus.equals("W")){
			b = true;
		}
		return b;
	}
	

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public void setAskTime(String askTime) {
		this.askTime = askTime;
	}

}
