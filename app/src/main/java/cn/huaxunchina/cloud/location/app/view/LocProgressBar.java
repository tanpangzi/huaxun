package cn.huaxunchina.cloud.location.app.view;

import android.widget.ProgressBar;
/**
 * 历史轨迹的进度
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月17日 下午2:31:50 
 *
 */
public class LocProgressBar {
	
	private ProgressBar progressBar;
	private int length;
	
	public LocProgressBar(ProgressBar progressBar){
	this.progressBar=progressBar;
	}
	
	public void setProgressLength(int length){
	this.length=length;
	}
	
	public void setProgress(int _count){
	int count=(int) (_count*100/length);
	this.progressBar.setProgress(count);
	}
	
	

}
