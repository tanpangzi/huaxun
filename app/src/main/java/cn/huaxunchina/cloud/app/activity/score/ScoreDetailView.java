package cn.huaxunchina.cloud.app.activity.score;

import java.util.List;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.adapter.StudentScoreAdapter;
import cn.huaxunchina.cloud.app.model.StudentScore;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

/**
 * 任课老师考试详情
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月19日 下午4:06:21 
 *
 */
public class ScoreDetailView extends Fragment {
	
	private Activity activity;
	private ListView lvDetail;
	private StudentScoreAdapter adapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//初始化数据请求 initdata  
	}
	
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity=activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.score_detail_view, container,
				false);
		initView(view);
		return view;
	}
	
	private void initView(View view){
		lvDetail=(ListView)view.findViewById(R.id.score_detail_listview);
	}
	
	
	public void initData(List<StudentScore> data){
		adapter = new StudentScoreAdapter(data);
		lvDetail.setAdapter(adapter);
	}
	
}
