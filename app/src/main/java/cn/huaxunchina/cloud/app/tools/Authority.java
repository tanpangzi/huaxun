package cn.huaxunchina.cloud.app.tools;

import java.util.List;

import android.text.TextUtils;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.Students;
import cn.huaxunchina.cloud.app.model.UserManager;


public class Authority {

	private static Authority instance = null;  
    /* 私有构造方法，防止被实例化 */  
    private Authority() {  
    }  
    /* 静态工程方法，创建实例 */  
    public static Authority getInstance() {  
        if (instance == null) {  
            instance = new Authority();  
        }  
        return instance;  
    }  
	
    public void verification(Action action){
    	UserManager manager = LoginManager.getInstance().getUserManager();
    	String roleid = manager.curRoleId;
    	if(TextUtils.isEmpty(roleid)){
    		toast();
    		return;
    	}
    	int c_roleId = Integer.valueOf(roleid);
    	if(c_roleId==RolesCode.PARENTS){//如果当前角色是家长
    		List<Students> students = manager.students;
    		if(students==null){toast();return;}//信息不全
    		int count = students.size();
    		if(count==0||count<0){toast();return;}//判断是否有小孩
    		String u_id = manager.curStudentId;
    		if(TextUtils.isEmpty(u_id)){toast();return;}//判断当前id不为空
    		boolean isExist = false;
    		Students info = null;
    		for(int i=0;i<count;i++){
    			Students item = students.get(i);
    			if(item.getStudentId().equals(u_id)){
    				info=item;
    				isExist=true;
    			}
    		}
    		if(!isExist){toast();return;}//判断小孩是否存在
    		if(info==null){toast();return;}//信息不全
    		String classId = info.getClassId();
    		if(TextUtils.isEmpty(classId)){toast();return;}//信息不全
    	}
    	if(c_roleId!=RolesCode.PARENTS){//如果当前角色是老师
    		List<ClassInfo> classs = manager.classInfo;
    		if(classs==null){toast();return;}//信息不全
    		int count = classs.size();
    		if(count==0||count<0){toast();return;}//无班级信息无法使用模块 
    	}
    	action.doAction();
    	
    }
    
     
    
    
    
    private void toast(){
    	Toast.makeText(ApplicationController.getContext(), R.string.user_info_eorr,0).show();
    }
    
	 
	
	public interface Action{
		
		public abstract void doAction();
		
	}
	
  /* public static void main(String[] args) {
		
	   Authority.getInstance().verification(new Action(){
		@Override
		public void doAction() {
			// TODO Auto-generated method stub
			 
			
		}});
	}
*/
}

