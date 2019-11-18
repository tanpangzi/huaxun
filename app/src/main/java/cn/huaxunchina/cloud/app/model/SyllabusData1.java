package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;

public class SyllabusData1 implements Serializable {

	private List<Syllabus1> data[];

	public List<Syllabus1>[] getData() {
		return data;
	}

	public void setData(List<Syllabus1>[] data) {
		this.data = data;
	}
	

	 
	
}
