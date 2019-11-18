package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;
/**
 * 五次考试数据集
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月9日 下午3:00:22 
 *
 */
public class FiveScoresData implements Serializable {
	
	private List<FiveScoresInfo> data;

	public List<FiveScoresInfo> getData() {
		return data;
	}

	public void setData(List<FiveScoresInfo> data) {
		this.data = data;
	}

 
	

}
