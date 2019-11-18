package cn.huaxunchina.cloud.app.model.interaction;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class HomeSchoolListData implements Serializable {

	private String title; // 主题
	private String content; // 列表内容
	private String publishTime;// 发布时间
	private List<String> imgList; // 列表图片
	private String themeId; // 互动主题id
	private String isPrivate; // 是否私有

	public String getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
