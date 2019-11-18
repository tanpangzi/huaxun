package cn.huaxunchina.cloud.location.app.model.post;

/**
 * 历史轨迹
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月17日 上午9:40:50 
 *
 */
public class LocusModel extends BaseModel {
	
	private String c = "uGetGPSHistory";
	private String startTime;//开始时间
	private String endTime;//结束时间
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
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
	
	

}
