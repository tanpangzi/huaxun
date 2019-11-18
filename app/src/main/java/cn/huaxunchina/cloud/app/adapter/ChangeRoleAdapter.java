package cn.huaxunchina.cloud.app.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.ClassInfo;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.Role;
import cn.huaxunchina.cloud.app.model.Students;
import cn.huaxunchina.cloud.app.model.response.LoginData;
import cn.huaxunchina.cloud.app.tools.PreferencesHelper;
import cn.huaxunchina.cloud.app.tools.UserUtil;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.location.app.model.ClientAppIdUtil;
import cn.huaxunchina.cloud.location.app.model.LbsManager;

/**
 * 角色适配器
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月6日 上午10:11:48
 * 
 */
public class ChangeRoleAdapter extends BaseAdapter {

	// private Role rolelist[] = null;
	private List<ChangeInfo> list = new ArrayList<ChangeInfo>();
	@SuppressWarnings("unused")
	private Handler handler = null;
	private ChangeItemClickListener changeItemClickListener;
	private int selectItem;
	private Context context;
	private String roleType;
	

	public ChangeRoleAdapter(LoginData data,Context context,String roleType) {
		if (data != null) {
			// 角色信息的分析
			Role[] r_list = data.getUserinfo().getRoles();
			if (r_list != null) {
				int r_count = r_list.length;
				for (int i = 0; i < r_count; i++) {
					Role role = r_list[i];
					if (role.getRoleId() == RolesCode.PARENTS) {// 添加学生信息
						// =======
						List<Students> students = data.getStudents();
						if (students != null) {// 添加学生信息
							int s_count = students.size();
							for (int j = 0; j < s_count; j++) {
								ChangeInfo c_info = new ChangeInfo();
								Students s_info = students.get(j);
								c_info.setRoleId(role.getRoleId());
								c_info.setTitle(s_info.getName()+ " 家长");
								c_info.setStudentId(s_info.getStudentId());
								c_info.setRoleName(role.getRoleName());
								c_info.setImeiNum(s_info.getImeiNum());
								c_info.setClassId(s_info.getClassId());
								list.add(c_info);
							}
						}

					} else {// 添加其他角色信息
						ChangeInfo c_info = new ChangeInfo();
						c_info.setRoleId(role.getRoleId());
						c_info.setTitle(role.getRoleName());
						c_info.setStudentId(null);
						c_info.setRoleName(role.getRoleName());
						/*List<ClassInfo> listClass = data.getClassList();
						if(listClass!=null&&listClass.size()>0){
							c_info.setClassId(listClass.get(0).getClassId());	
						}else{
							c_info.setClassId("-1");
						}
						
						if(data.getTeacher()!=null){
						c_info.setTeacherId(data.getTeacher().getTeacherID());
						}*/
						
						list.add(c_info);
					}
				}
				// 排序
				ChangeComparator comparator = new ChangeComparator();
				Collections.sort(list, comparator);
			}

		}
		this.context=context;
		this.roleType=roleType;
		selectItem=UserUtil.getChangRolePostion();
		
	}

	/*
	 * public void setHttpUtils(HttpUtils httpUtils) { this.httpUtils =
	 * httpUtils; }
	 */

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}
	public int getSelectItem() {
		return selectItem;
	}

	@Override
	public int getCount() {

		int count = list.size();
		return count;
	}

	@Override
	public Object getItem(int arg0) {

		return null;
	}

	@Override
	public long getItemId(int arg0) {

		return 0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder holder = null;
		final ChangeInfo role = list.get(position);
		if (view == null) {
			view = View.inflate(ApplicationController.getContext(),R.layout.changerole_item, null);
			holder = new ViewHolder();
			holder.llChangeRole = (Button) view.findViewById(R.id.change_layout);
			holder.llChangeRole.setBackgroundResource(R.drawable.change_role_bg);
			if (position ==selectItem&&!roleType.equals("1")) {
			holder.llChangeRole.setBackgroundResource(R.color.actionbar_bg);
			holder.llChangeRole.setTextColor(context.getResources().getColor(R.color.white));
			}else{
				holder.llChangeRole.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						setSelectItem(position);
						int roleid = role.roleId;
						PreferencesHelper pre = new PreferencesHelper(ApplicationController.getContext(), UserUtil.getUserId());
						LoginManager manager = LoginManager.getInstance();
						if (roleid==RolesCode.PARENTS) {// 如果是家长就保存当前小孩的// studentId
							ClientAppIdUtil.setLbsClientAppId("");
							
							manager.setImei(role.getImeiNum());//保存imei号
							manager.setCurRoleId(pre, roleid+"");
							manager.setCurStudentId(pre, role.getStudentId());
							manager.setCurClassId(pre,role.getClassId());
						}else{
							//manager.setImei("");
							ClientAppIdUtil.setLbsClientAppId("");
							manager.setCurRoleId(pre, roleid+"");
							//manager.setCurTeacherId(pre, role.getTeacherId());
						}
						 
						changeItemClickListener.onItemClickListener(role);
					}
				});
			}
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		String roleName = role.title;
		holder.llChangeRole.setText(roleName);

		return view;
	}

	public class ViewHolder {
		private Button llChangeRole;
	}

	public class ChangeInfo {
		int roleId;
		String title;
		String studentId;
		String teacherId;
		String roleName;
		String imeiNum;
		String classId;
		
		

		public String getTeacherId() {
			return teacherId;
		}

		public void setTeacherId(String teacherId) {
			this.teacherId = teacherId;
		}

		public String getClassId() {
			return classId;
		}

		public void setClassId(String classId) {
			this.classId = classId;
		}

		public String getImeiNum() {
			if(imeiNum==null) return "";
			return imeiNum;
		}

		public void setImeiNum(String imeiNum) {
			this.imeiNum = imeiNum;
		}

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		public int getRoleId() {
			return roleId;
		}

		public void setRoleId(int roleId) {
			this.roleId = roleId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getStudentId() {
			return studentId;
		}

		public void setStudentId(String studentId) {
			this.studentId = studentId;
		}

	}

	public class ChangeComparator implements Comparator<ChangeInfo> {

		@Override
		public int compare(ChangeInfo arg0, ChangeInfo arg1) {

			if (arg0.getRoleId() < arg1.getRoleId()) {
				return -1;
			}
			if (arg0.getRoleId() > arg1.getRoleId()) {
				return 1;
			}
			return 0;

		}

	}

	public void setChangeItemClickListener(
			ChangeItemClickListener changeItemClickListener) {
		this.changeItemClickListener = changeItemClickListener;
	}

	public interface ChangeItemClickListener {
		public void onItemClickListener(ChangeInfo role);
	}
}
