package cn.huaxunchina.cloud.app.activity.score;


import java.util.List;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.adapter.StudentScoreAdapter;
import cn.huaxunchina.cloud.app.model.StudentScore;
import cn.huaxunchina.cloud.app.model.StudentScoreData;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
/**
 * 班主任查看学生考试详情
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月5日 下午3:18:47 
 *
 */
public class ClassStudentScoreDetail extends BaseActivity{
	
	private TextView tvType = null;
	private TextView tvTime = null;
	private ListView lvScore = null;
	private MyBackView back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score_student_detail_activity);
		loadingDialog = new LoadingDialog(this);
		StudentScoreData data =(StudentScoreData) this.getIntent().getSerializableExtra("studentScoreData");
		String examTime = this.getIntent().getStringExtra("examTime");
		initView();
		if(data!=null){
		List<StudentScore> list = data.getData();
		if(list!=null&&list.size()>0){
			StudentScore info =  data.getData().get(0);
			String title = info.getStudentName();
//			initBar(title);
			back.setBackText(title);
			back.setAddActivty(this);
			lvScore.setAdapter(new StudentScoreAdapter(list));
			tvType.setText(info.getExamTypeName());
			tvTime.setText(examTime);
		}
		}else{
			back.setBackText("考试详情");
			back.setAddActivty(this);
		}
		 
		 
	}
	
	
	
	public void initView() {
		tvType=(TextView)findViewById(R.id.score_student_type);
		tvTime=(TextView)findViewById(R.id.score_student_time);
		lvScore=(ListView)findViewById(R.id.score_student_listview);
		back=(MyBackView) findViewById(R.id.back);
		 
	}
	 
	

}
