package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;
/**
 *考勤 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月22日 上午10:04:18 
 *
 */
@SuppressWarnings("serial")
public class AttendanceInfoData implements Serializable {

	private List<AttendanceInfo> data;

	public List<AttendanceInfo> getData() {
		return data;
	}

	public void setData(List<AttendanceInfo> data){
		this.data = data;
	}
	
	
}
