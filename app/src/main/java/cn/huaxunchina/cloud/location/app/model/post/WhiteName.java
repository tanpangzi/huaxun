package cn.huaxunchina.cloud.location.app.model.post;

import java.io.Serializable;

/**
 * 白名单请求参数
 * @author zoupeng@huaxunchina.cn
 *
 * 2015-1-27 下午2:45:50
 */
@SuppressWarnings("serial")
public class WhiteName extends BaseModel implements Serializable {
	private String c = "uWhitelist";
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
