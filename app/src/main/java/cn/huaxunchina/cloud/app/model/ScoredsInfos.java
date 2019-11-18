package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
/**
 * 五次考试的成绩详情
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年10月31日 上午11:07:07 
 *
 */
public class ScoredsInfos implements Serializable {
	
	private String examNo;//考试名称
	private int score;//成绩
	
	public String getExamNo() {
		return examNo;
	}
	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	

}
