package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;
/**
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月21日 上午8:58:14 
 *
 */
@SuppressWarnings("serial")
public class NewsInfo implements Serializable {

	private int type;//1-家庭作业   2-通知公告  3-每日一读  4-家校互动
	private Boolean isnew;
	private List<NewsConten> contentList;
	
	 
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Boolean getIsnew() {
		return isnew;
	}
	public void setIsnew(Boolean isnew) {
		this.isnew = isnew;
	}
	public List<NewsConten> getContentList() {
		return contentList;
	}
	public void setContentList(List<NewsConten> contentList) {
		this.contentList = contentList;
	}
	 
	
	
}
