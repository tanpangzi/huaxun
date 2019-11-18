package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
/**
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月21日 上午8:58:01 
 *
 */
@SuppressWarnings("serial")
public class NewsConten implements Serializable {

	private String id;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
