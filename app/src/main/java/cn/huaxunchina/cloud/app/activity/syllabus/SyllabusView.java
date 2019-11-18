package cn.huaxunchina.cloud.app.activity.syllabus;


import java.util.ArrayList;
import java.util.List;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.adapter.SyllabusAdapter2;
import cn.huaxunchina.cloud.app.model.Syllabus1;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

/**
 * 课程表Veiw
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年8月7日 上午11:58:45 
 *
 */
public class SyllabusView extends Fragment {

	private Context context;
  	private ListView lvSyllabus;
  	private SyllabusAdapter2 adapter;
  	
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
		context=activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.syllabus_layout, container,false);
		lvSyllabus=(ListView)view.findViewById(R.id.syllabus_listview);
		return view;
	}
	 
	
	public void initData(List<Syllabus1> data){
		adapter = new SyllabusAdapter2(data);
		lvSyllabus.setAdapter(adapter);
	}
	
	
}
