package cn.huaxunchina.cloud.app.getui.view;

import cn.huaxunchina.cloud.app.getui.GetuiUtil;
import cn.huaxunchina.cloud.app.getui.GetuiUtil.GetuidoActivity;
import cn.huaxunchina.cloud.app.view.ServiceUiDialog;
import cn.huaxunchina.cloud.app.view.ServiceUiDialog.LookActivity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.WindowManager;

public class UiService extends Service implements GetuidoActivity,LookActivity{
	
	private String categoryId = null;
	private String msgContent = "";
	private String id = "";
	private ServiceUiDialog dialog;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		 
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
        if(intent!=null){
        	Bundle bunde = intent.getExtras();
            categoryId =bunde.getString("categoryId");
            msgContent = bunde.getString("msgContent");
            id = bunde.getString("id");
        }
		Context contenxt = this.getBaseContext();
		//Toast.makeText(contenxt, "myService["+categoryId+"]", Toast.LENGTH_LONG).show();
		if(categoryId!=null){
			if(dialog!=null&&dialog.isShowing()){//保持只有一个 Dialog
				dialog.dismiss();
			}
			dialog = new ServiceUiDialog(contenxt,categoryId,msgContent);
			dialog.setLookActivity(this);
			dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
			dialog.show();
			}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void doActivity(Intent intent) {
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY
	    startActivity(intent);	
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		 
	}

	@Override
	public void onClickLookBtn() {
		// TODO Auto-generated method stub
		GetuiUtil.getInstance().setGetuidoActivity(this);
		GetuiUtil.getInstance().categoryType(categoryId,id, UiService.this);
	}

	 
	

}
