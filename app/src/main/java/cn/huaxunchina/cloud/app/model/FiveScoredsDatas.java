package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;
/**
 * 五次考试成绩
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年10月31日 上午11:14:44 
 *
 */
public class FiveScoredsDatas implements Serializable {
	
	private List<FiveScoredsInfos> data;

	public List<FiveScoredsInfos> getData() {
		return data;
	}

	public void setData(List<FiveScoredsInfos> data) {
		this.data = data;
	}
	

}
