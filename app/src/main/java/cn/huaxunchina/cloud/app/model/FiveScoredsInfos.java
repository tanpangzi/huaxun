package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;
/**
 *  科目考试详情
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年10月31日 上午11:13:31 
 *
 */
public class FiveScoredsInfos implements Serializable {
	
	private String courseName;//科目名称
	private List<ScoredsInfos> scores;//成绩集合
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public List<ScoredsInfos> getScores() {
		return scores;
	}
	public void setScores(List<ScoredsInfos> scores) {
		this.scores = scores;
	}
	
	

}
