package cn.huaxunchina.cloud.app.activity.score;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.adapter.StudentScoreAdapter;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.ScoreModel;
import cn.huaxunchina.cloud.app.imp.ScoreResponse;
import cn.huaxunchina.cloud.app.model.StudentScore;
import cn.huaxunchina.cloud.app.model.StudentScoreData;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
/**
 * 家长考试详情
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月5日 下午3:18:47 
 *
 */
public class StudentScoreDetail extends BaseActivity{
	
	private TextView tvType = null;
	private TextView tvTime = null;
	private ListView lvScore = null;
	private ScoreResponse  response;
	private String examId = "0";
	private String studentId = null;
	private LoadingDialog loadingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_student_detail_activity);
		loadingDialog = new LoadingDialog(this);
		StudentScore info =(StudentScore) this.getIntent().getSerializableExtra("studentScore");
		studentId=info.getStudentId();
		examId=info.getExamId();
		String title = info.getStudentName();
		initBar(title);
		initView(info);
		initData();
	}
	
	
	
	
	
	
	
	public void initView(StudentScore info) {
		tvType=(TextView)findViewById(R.id.score_student_type);
		tvTime=(TextView)findViewById(R.id.score_student_time);
		lvScore=(ListView)findViewById(R.id.score_student_listview);
		tvType.setText(info.getExamTypeName());
	}
	
	
	private void initData(){
		loadingDialog.show();
		response = new ScoreModel();
		response.scoreDetail(examId, studentId, null, application.httpUtils, handler);
	}
	
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				loadingDialog.dismiss();
				StudentScoreData data = (StudentScoreData)msg.obj;
				lvScore.setAdapter(new StudentScoreAdapter(data.getData()));
				tvType.setText(data.getData().get(0).getExamTypeName());
				break;
			}
		}
	};
	

}
