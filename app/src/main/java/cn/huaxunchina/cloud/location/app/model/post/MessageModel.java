package cn.huaxunchina.cloud.location.app.model.post;

@SuppressWarnings("serial")
public class MessageModel extends BaseModel {
	
	private String c = "selectMessage";
	private int id;
	
	public MessageModel(){
		super();
	}
	
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
