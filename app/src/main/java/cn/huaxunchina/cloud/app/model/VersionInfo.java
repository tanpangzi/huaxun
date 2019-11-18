package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;
/**
 *版本信息 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月4日 上午9:53:32 
 *
 */
@SuppressWarnings("serial")
public class VersionInfo implements Serializable {

	private double version;
	private List<String> desription;
	public double getVersion() {
		return version;
	}
	public void setVersion(double version) {
		this.version = version;
	}
	public List<String> getDesription() {
		return desription;
	}
	public void setDesription(List<String> desription) {
		this.desription = desription;
	}
	
	
	 
	
	
	
}
