package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
import java.util.List;
/**
 * 服务器数据模型
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月24日 下午4:54:06 
 *
 */
@SuppressWarnings("serial")
public class ServerData implements Serializable{
	
	private List<ServerInfo> data;
	
	public List<ServerInfo> getData() {
		return data;
	}
	public void setData(List<ServerInfo> data) {
		this.data = data;
	}
	
	public class ServerInfo implements Serializable{
		private String area;//平台名称
		private String domain;//平台地址
		private String id;//平台号
		private String name;//
		private String msgServerUrl;//推送服务器url
		
		
		public String getMsgServerUrl() {
			return msgServerUrl;
		}
		public void setMsgServerUrl(String msgServerUrl) {
			this.msgServerUrl = msgServerUrl;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getDomain() {
			return domain;
		}
		public void setDomain(String domain) {
			this.domain = domain;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}

}
