package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;
/**
 * 考勤信息
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月21日 下午3:32:53 
 * 
 */
@SuppressWarnings("serial")
public class AttendanceInfo implements Serializable {
	
	private String name;
	private List<AttDay> cardata;
	private int attType;
	
	 
	public int getAttType() {
		return attType;
	}
	public void setAttType(int attType) {
		this.attType = attType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AttDay> getCardata() {
		return cardata;
	}
	public void setCardata(List<AttDay> cardata) {
		this.cardata = cardata;
	}



	public class AttDay implements Serializable{
		private int attendanceType;
		private String cardName;
		private String result;
		private String signData;
		private String signTime;
		public int getAttendanceType() {
			return attendanceType;
		}
		public void setAttendanceType(int attendanceType) {
			this.attendanceType = attendanceType;
		}
		public String getCardName() {
			return cardName;
		}
		public void setCardName(String cardName) {
			this.cardName = cardName;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getSignData() {
			return signData;
		}
		public void setSignData(String signData) {
			this.signData = signData;
		}
		public String getSignTime() {
			return signTime;
		}
		public void setSignTime(String signTime) {
			this.signTime = signTime;
		}
		
	}
	
}
