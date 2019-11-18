package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;

/**
 * 查询亲情号指令
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-12 下午2:07:48
 */
@SuppressWarnings("serial")
public class QueryFamily extends BaseModel implements Serializable {
	private String c = "uRelateNumber";
	private String type;// 1—查询；2-添加 3-修改 4-删除

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
