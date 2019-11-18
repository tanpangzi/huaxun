package cn.huaxunchina.cloud.app.model;

import java.io.Serializable;
/**
 * 短信群发数据对象
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年2月11日 下午4:55:22 
 *
 */
@SuppressWarnings("serial")
public class SendSmsData implements Serializable {
	
   private SendSmsItem[] data;
   private String content;
   private int type = 1;//0.单发  2.单组发  3.多组发
    
	public int getType() {
	return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
	return content;
    }
	public void setContent(String content) {
		this.content = content;
	}
	public SendSmsData(int size){
    	data = new  SendSmsItem[size];
    }
	public SendSmsItem[] getData() {
		return data;
	}
	
	public void setData(SendSmsItem data[]) {
		this.data = data;
	}
	
	public void add(int index,SendSmsItem item) {
		this.data[index]=item;
	}

}
