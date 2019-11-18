package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
/**
 * News标示
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年11月10日 下午3:37:09 
 *
 */
public class NewsMarking implements Serializable {
	
	private String key;
	private String id;
	 
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

}
