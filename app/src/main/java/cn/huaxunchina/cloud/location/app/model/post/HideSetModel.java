package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;

/**
 * 添加或修改或删除隐身设置指令
 * @author zoupeng@huaxunchina.cn
 *
 * 2015-1-12 下午4:42:47
 */
@SuppressWarnings("serial")
public class HideSetModel extends BaseModel implements Serializable {
     private String c="classHiding";
	 private int type;       //1—查询，2~添加或者修改或者删除3~ 未定义
	 private String onOff;  //功能开关，总共7个字节，C1……C7，每一个字节表示一周的某一天，C1表示周一，C7表示周日，若某一字节是0，表示当天关闭此功能，若某一字节是1，表示当天打开此功能。
	 private String TimeFrom1; //TimeFromX：从，格式为HH:MM，只支持24小时制，X从1-3，1表示上午，2表示下午，3表示晚上
	 private String TimeTo1;
	 private String TimeFrom2;  //TimeToX：到，格式为HH:MM，只支持24小时制，X从1-3，1表示上午，2表示下午，3表示晚上
	 private String TimeTo2;    //若上午、下午或者晚上的时间为空，表示此时间段的隐身功能关闭。
	 private String TimeFrom3;
	 private String TimeTo3;
	 
	 
	 public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOnOff() {
		return onOff;
	}
	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}
	public String getTimeFrom1() {
		return TimeFrom1;
	}
	public void setTimeFrom1(String timeFrom1) {
		TimeFrom1 = timeFrom1;
	}
	public String getTimeTo1() {
		return TimeTo1;
	}
	public void setTimeTo1(String timeTo1) {
		TimeTo1 = timeTo1;
	}
	public String getTimeFrom2() {
		return TimeFrom2;
	}
	public void setTimeFrom2(String timeFrom2) {
		TimeFrom2 = timeFrom2;
	}
	public String getTimeTo2() {
		return TimeTo2;
	}
	public void setTimeTo2(String timeTo2) {
		TimeTo2 = timeTo2;
	}
	public String getTimeFrom3() {
		return TimeFrom3;
	}
	public void setTimeFrom3(String timeFrom3) {
		TimeFrom3 = timeFrom3;
	}
	public String getTimeTo3() {
		return TimeTo3;
	}
	public void setTimeTo3(String timeTo3) {
		TimeTo3 = timeTo3;
	}
	
	 
}
