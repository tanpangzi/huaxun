package cn.huaxunchina.cloud.app.activity.score;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.adapter.ClassAdapter;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.ScoreModel;
import cn.huaxunchina.cloud.app.imp.ScoreResponse;
import cn.huaxunchina.cloud.app.model.StudentScoreData;
import cn.huaxunchina.cloud.app.view.MyBackView;

/**
 * 班级成员详情
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年7月24日 下午4:43:04
 */
public class ClassScore extends BaseActivity {

	private GridView lvScoredetail;
	private ClassAdapter adapter;
	private ScoreResponse response;
	private MyBackView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classscore_activity);
		response = new ScoreModel();
//		initBar("考试详情");
		initView();
		initData();
	}

	@Override
	public void initView() {
		super.initView();
		back=(MyBackView) findViewById(R.id.back);
		back.setBackText("考试详情");
		back.setAddActivty(this);
		lvScoredetail = (GridView) findViewById(R.id.score_gridview);
		lvScoredetail.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				goActivity(ScoreDetail.class);

			}
		});
	}

	private void initData() {
		String examId = "1";
		String studentId = "1";
		String teacherId = null;
		response.scoreDetail(examId, studentId, teacherId, httpUtils, handler);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				StudentScoreData data = (StudentScoreData) msg.obj;
				lvScoredetail.setAdapter(new ClassAdapter(data.getData(),getBaseContext()));
				break;
			}
		};
	};

}
