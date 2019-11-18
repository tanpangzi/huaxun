package cn.huaxunchina.cloud.app.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import cn.huaxunchina.cloud.app.model.StudentScore;
import cn.huaxunchina.cloud.app.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * TODO(描述)
 * 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2014年9月17日 上午10:37:00
 * 
 */
public class ClassAdapter extends BaseAdapter {

	List<StudentScore> data;
	Map<String, List<StudentScore>> studentScoreMap = new HashMap<String, List<StudentScore>>();
	private Context mcontext;
    private int mseclection=-1;
    
	public ClassAdapter(List<StudentScore> data, Context mcontext) {
		this.data = initData(data);
		this.mcontext = mcontext;
	}

	public List<StudentScore> getStudentScore(int id) {
		String studentId = data.get(id).getStudentId();
		return studentScoreMap.get(studentId);
	}

	@Override
	public int getCount() {
		int count = 0;
		if (data != null) {
			count = data.size();
		}
		return count;
	}

	public void setSeclection(int position){
		this.mseclection=position;
		notifyDataSetChanged();
	}
	
	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int itemId, View view, ViewGroup arg2) {
		ViewHolder holder = null;
		if (view == null) {
			view = View.inflate(mcontext,R.layout.classscore_item, null);
			holder = new ViewHolder();
			holder.bg_liner = (LinearLayout) view.findViewById(R.id.bg_liner);
			holder.tvCourse = (TextView) view.findViewById(R.id.score_course);
			holder.tvStudentName = (TextView) view.findViewById(R.id.student_name);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		if(mseclection==itemId){
			holder.bg_liner.setBackgroundResource(R.drawable.classscore_item_bg1);
		}else{
			holder.bg_liner.setBackgroundResource(R.drawable.classscore_item_bg);
		}
		StudentScore score = data.get(itemId);
		holder.tvCourse.setText(getCountScore(score.getStudentId()));
		holder.tvStudentName.setText(score.getStudentName());
		return view;
	}

	class ViewHolder {
		private LinearLayout bg_liner;
		public TextView tvCourse;
		public TextView tvStudentName;
	}

	// 学生的成绩统计
	private List<StudentScore> initData(List<StudentScore> data) {
		List<StudentScore> new_data = new ArrayList<StudentScore>();

		Map<String, StudentScore> map = new HashMap<String, StudentScore>();
		if (data != null) {
			int data_size = data.size();
			// 感觉学生统计总成绩
			for (int i = 0; i < data_size; i++) {
				StudentScore info = data.get(i);
				String studentid = info.getStudentId();
				StudentScore m_info = map.get(studentid);
				if (m_info == null) {
					map.put(studentid, info);
					// ==单个学生的成绩分类统计
					List<StudentScore> new_score = new ArrayList<StudentScore>();
					new_score.add(info);
					studentScoreMap.put(studentid, new_score);

				} else {
					// ====单个学生的成绩分类统计
					List<StudentScore> new_score = studentScoreMap
							.get(studentid);
					new_score.add(info);
					studentScoreMap.put(studentid, new_score);

				}
			}

			// map list
			Set<Entry<String, StudentScore>> set = map.entrySet();
			Iterator<Entry<String, StudentScore>> iterator = set.iterator();
			while (iterator.hasNext()) {
				StudentScore info = iterator.next().getValue();
				new_data.add(info);
			}

		}
		// 重新赋值
		return new_data;
	}

	// 计算总分
	private String getCountScore(String studentId) {
		float score = 0;
		List<StudentScore> new_score = studentScoreMap.get(studentId);
		int size = new_score.size();
		for (int i = 0; i < size; i++) {
			StudentScore info = new_score.get(i);
			float m_score = Float.valueOf(info.getScore());
			score = score + m_score;
		}
		return String.valueOf(((int) score));

	}

}
