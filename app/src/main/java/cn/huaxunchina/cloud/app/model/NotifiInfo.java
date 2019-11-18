package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;
/**
 * 推送消息数据模型
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月12日 上午11:01:57 
 * 
 * 
{"msg":"尊敬的家长d您好o您的孩子yy已于ttt安全离校","extras":{"ref":0,"ids":[24010],"type":1,"cid":2},"sendNo":"a36063063427375_1"}
msg=内容
extras=附加属性
sendNo=序列号
张瑜灿  16:45:35
ref关联id,0或空表示没有关联
type对应原来的groupType
cid对应原来的categoryId
ids
 *
 */
public class NotifiInfo implements Serializable {
	
	private String msg;//内容
	private String sendNo;//变化
	private NotifiExtras extras;//标示
	 
 

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSendNo() {
		return sendNo;
	}

	public void setSendNo(String sendNo) {
		this.sendNo = sendNo;
	}


	public NotifiExtras getExtras() {
		return extras;
	}

	public void setExtras(NotifiExtras extras) {
		this.extras = extras;
	}



	public class NotifiExtras implements Serializable {
		
	 
		 
		
	private int t;//1单个用户 2多个用户  3 群体(tag用户) idc_c 班级  ids_s学校 g
	private List<String> ids;//用户id / classId /学校id
	private int c;//1=通知公告    /2=考勤 /3=成绩  /4=家校互动  /5=家庭作业  /6=请假 /100推送
	private int r;//ref关联id
	
	
	 
	 
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	public int getC() {
		return c;
	}
	public void setC(int c) {
		this.c = c;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
 
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
	}

}
