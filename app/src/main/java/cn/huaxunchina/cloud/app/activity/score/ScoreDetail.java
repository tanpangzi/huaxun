package cn.huaxunchina.cloud.app.activity.score;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.UserManager;

/**
 * 成绩详情
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月19日 下午7:10:43
 * 
 */
public class ScoreDetail extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_layout);
		// String examId = this.getIntent().getStringExtra("examId");
		Fragment fm = null;
		UserManager manager = LoginManager.getInstance().getUserManager();
		String curRoleId = manager.curRoleId;

		if (curRoleId.equals(String.valueOf(RolesCode.PARENTS))) {// 家长
			fm = new ScoreDetailFragment();
			addFragment(fm);
		} else if (curRoleId.equals(String.valueOf(RolesCode.TEACHER)))// 任课老师
		{
			fm = new ScoreDetailFragment2();
			addFragment(fm);
		} else if (curRoleId.equals(String.valueOf(RolesCode.HEAD_TEACHER)))// 班主任
		{
			fm = new ScoreDetailFragment3();
			addFragment(fm);
		}

	}

	private void addFragment(Fragment fm) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.fragment_container, fm);
		ft.commit();
	}
}
