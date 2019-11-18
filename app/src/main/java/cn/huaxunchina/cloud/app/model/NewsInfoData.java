package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月21日 上午8:58:08 
 *
 */
public class NewsInfoData implements Serializable {

	private List<NewsInfo> data;

	public List<NewsInfo> getData() {
		return data;
	}

	public void setData(List<NewsInfo> data) {
		this.data = data;
	}
	
}
