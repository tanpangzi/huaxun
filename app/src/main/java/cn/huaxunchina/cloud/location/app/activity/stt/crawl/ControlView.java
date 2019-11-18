package cn.huaxunchina.cloud.location.app.activity.stt.crawl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.view.ContactsDialog;
import cn.huaxunchina.cloud.location.app.model.MarkerInfo;
import cn.huaxunchina.cloud.location.app.model.post.Circle;
import cn.huaxunchina.cloud.location.app.model.post.FencesettingModel;
import cn.huaxunchina.cloud.location.app.network.HttpHandler;
import cn.huaxunchina.cloud.location.app.network.HttpHandler.HttpHandlerCallBack;

public class ControlView {
	
	public Button add_crawl;
	public Button det_crawl;
	public Button del_crawl;
	private MarkerInfo markerInfo;
	private FencingActivity activitys;
	private FencingView fencingView;
	
	public ControlView(FencingActivity activity,Button add_crawl,Button det_crawl,Button del_crawl){
		this.add_crawl=add_crawl;
		this.det_crawl=det_crawl;
		this.del_crawl=del_crawl;
		this.activitys=activity;
		add_crawl.setVisibility(View.GONE);
		det_crawl.setVisibility(View.GONE);
		del_crawl.setVisibility(View.GONE);
		 
		add_crawl.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			addCrawl();
		}
		});
		
		det_crawl.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			detCrawl();
		}
		});
		
		del_crawl.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
		Circle circle = markerInfo.circle;
		ContactsDialog dialog = new ContactsDialog(activitys);
		dialog.show();
		dialog.setTitle("提示!");
		dialog.setMessage("确定要删除"+circle.getPositionName()+"围栏?");
		dialog.setNegativeButton("取消");
		dialog.setPositiveButton("确定");
		dialog.setOkOnClickListener(new ContactsDialog.OnClickListener(){
		@Override
		public void onClick() {
		delCrawl();
		}});
				
		}
		});
	}
	
	public void setFencingView(FencingView fencingView){
		this.fencingView=fencingView;
	}
	
	
	public void hideOperationView(){
		add_crawl.setVisibility(View.GONE);
		det_crawl.setVisibility(View.GONE);
		del_crawl.setVisibility(View.GONE);
	}
	
	public void operation(MarkerInfo markerInfo){
		if(markerInfo.datatype==0){
			add_crawl.setVisibility(View.VISIBLE);
			det_crawl.setVisibility(View.GONE);
			del_crawl.setVisibility(View.GONE);
		}else{
			add_crawl.setVisibility(View.GONE);
			det_crawl.setVisibility(View.VISIBLE);
			del_crawl.setVisibility(View.VISIBLE);
		} 
		this.markerInfo=markerInfo;
		
	}
	
	private void addCrawl(){
		if(markerInfo!=null){
		Circle circle = markerInfo.circle;
		Intent intent = new Intent(activitys,AddElectronicFence.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("data", circle);
		intent.putExtras(bundle);
		activitys.startActivityForResult(intent, ResultCode.FEN_DEL_CODE);
		}
		
	}
	
	private void delCrawl(){
		if(markerInfo!=null){
		FencesettingModel fen = new FencesettingModel();//删除
		fen.setType(4);
		fen.setCircle_id(markerInfo.circle.getCircle_id());
		new HttpHandler(activitys.loadingDialog,activitys.applicationController.httpUtils_lbs,fen,new HttpHandlerCallBack(){
		@Override
		public void onCallBack(String json) {
		if(fencingView!=null){
		fencingView.hide();
		fencingView.remove(markerInfo);
		}
		}
		@Override
		public void onErro() {
		}});
		}
	}
	
	
	private void detCrawl(){
		if(markerInfo!=null){
		Circle circle = markerInfo.circle;
		Intent intent = new Intent(activitys,AddElectronicFence.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("data", circle);
		intent.putExtras(bundle);
		activitys.startActivityForResult(intent, ResultCode.FEN_DEL_CODE);
		}
	}
}


