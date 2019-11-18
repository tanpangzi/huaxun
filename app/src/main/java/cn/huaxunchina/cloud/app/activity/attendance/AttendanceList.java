package cn.huaxunchina.cloud.app.activity.attendance;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;

/**
 * 
 * 考勤管理列表
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月13日 上午9:29:12 
 *
 */
public class AttendanceList extends BaseActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_layout);
		 Fragment fm = null;
		 UserManager manager = LoginManager.getInstance().getUserManager();
		 String curRoleId = manager.curRoleId;
		 if(curRoleId.equals(String.valueOf(RolesCode.PARENTS))){//角色的判断
			 fm =new AttendancePatriarchFragment(); 
		 }else{
			 fm = new AttendanceTeacherFragment();
		 }
		 addFragment(fm);
	}
	
	
	private void addFragment(Fragment fm){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.fragment_container,fm);
		ft.commit();
	}
	

}
