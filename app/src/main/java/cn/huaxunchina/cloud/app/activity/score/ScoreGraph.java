package cn.huaxunchina.cloud.app.activity.score;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.R;

/**
 * 成绩曲线图
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月6日 下午5:21:28
 * 
 */
public class ScoreGraph extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_layout);
		String examId = this.getIntent().getStringExtra("examId");
		String examName = this.getIntent().getStringExtra("examName");
		Fragment fm = new ScoreGraphFragment();
		Bundle bundle = new Bundle();
		bundle.putString("examId", examId);
		bundle.putString("examName", examName);
		fm.setArguments(bundle);
		addFragment(fm);

	}

	private void addFragment(Fragment fm) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.fragment_container, fm);
		ft.commit();
	}

}
