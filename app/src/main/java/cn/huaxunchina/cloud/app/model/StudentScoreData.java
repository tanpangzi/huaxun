package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;
/**
 * 学生考试成绩
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月14日 下午3:51:57 
 *
 */
public class StudentScoreData implements Serializable{
	
	 private List<StudentScore > data;

	public List<StudentScore> getData() {
		return data;
	}

	public void setData(List<StudentScore> data) {
		this.data = data;
	}

	 

}
