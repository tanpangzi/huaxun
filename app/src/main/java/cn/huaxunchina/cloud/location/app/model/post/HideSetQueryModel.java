package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;

/**
 * 查询隐身设置指令
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2015-1-12 下午4:42:47
 */
@SuppressWarnings("serial")
public class HideSetQueryModel extends BaseModel implements Serializable {
	private String c = "classHiding";
	private int type; // 1—查询，2~添加或者修改或者删除3~ 未定义

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

}
