package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;
/**
 * 围栏设置
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月4日 下午3:21:04 
 *
 */
@SuppressWarnings("serial")
public class FencesettingModel extends BaseModel implements Serializable {
	
	private String c = "fencesetting";//
	private int type;//1—查询；2-添加 3-修改 4-删除
	private int Circle_id;
	private Circle circle;
	
	public FencesettingModel(){
		super();
	}
	
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	 
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCircle_id() {
		return Circle_id;
	}
	public void setCircle_id(int circle_id) {
		Circle_id = circle_id;
	}
	public Circle getCircle() {
		return circle;
	}
	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	
	
	

}
