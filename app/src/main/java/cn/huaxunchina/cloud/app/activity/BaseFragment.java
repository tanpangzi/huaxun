package cn.huaxunchina.cloud.app.activity;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
	
	protected boolean isVisible;
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
            System.out.println("Fragment出现了==============="+isVisible);
        } else {
            isVisible = false;
            onInvisible();
            System.out.println("Fragment不见了=============="+isVisible);
        }
    }
 
    protected void onVisible(){
    	loadData();
    }
 
    protected abstract void loadData();
 
    protected void onInvisible(){}
    
    public void showLoginDialog(final Context mcontext) {
    	
    	Toast.makeText(mcontext, "已断开,请重新登录", Toast.LENGTH_SHORT).show();
    	//ApplicationController.exit();
		//Intent intent = new Intent();
		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		//intent.setClass(mcontext, Login.class);
		//mcontext.startActivity(intent);
		
    	
		/*SingleDialog dialog = new SingleDialog(mcontext, R.style.dialog);
		dialog.setTitle("提示");
		dialog.setContent("已断开,请重新登陆");
		dialog.setButtonText("重新登陆");
		dialog.setButtonClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ApplicationController.exit();
				Intent intent = new Intent();
				intent.setClass(mcontext, Login.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
				mcontext.startActivity(intent);
				
			}
		});
		dialog.show();*/
	}
    

}
