package cn.huaxunchina.cloud.app.adapter;

import java.util.List;

import cn.huaxunchina.cloud.app.common.Constant.RolesCode;
import cn.huaxunchina.cloud.app.model.LoginManager;
import cn.huaxunchina.cloud.app.model.Score;
import cn.huaxunchina.cloud.app.model.UserManager;
import cn.huaxunchina.cloud.app.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScoreAdapter extends BaseAdapter {

	private Context context;
	private List<Score> listdata;
	private String curRoleId = null;

	public ScoreAdapter(Context context, List<Score> listdata) {
		this.context = context;
		this.listdata = listdata;
		UserManager manager = LoginManager.getInstance().getUserManager();
		curRoleId = manager.curRoleId;
	}

	@Override
	public int getCount() {
		int count = 0;
		if (listdata != null) {
			count = listdata.size();
		}
		return count;
	}

	public void addItem(List<Score> data, boolean next) {
		if (next) {
			if (data != null) {
				listdata.addAll(data);
				notifyDataSetInvalidated();
			}
		} else {
			if (data != null) {
				this.listdata=data;
				notifyDataSetInvalidated();
			}
		}

	}

	public Score getItemScore(int id) {
		return listdata.get(id);
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
	public View getView(int id, View view, ViewGroup arg2) {
		ViewHolder holder = null;
		if (view == null) {
			view = View.inflate(context, R.layout.scorelist_item, null);
			holder = new ViewHolder();
			holder.tvContent = (TextView) view.findViewById(R.id.score_content);
			holder.tvTime = (TextView) view.findViewById(R.id.score_time);
			holder.tvClassName = (TextView) view.findViewById(R.id.score_classname);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		Score score = listdata.get(id);
		holder.tvContent.setText(score.getExamNo());
		holder.tvTime.setText(score.getExamTime());
		if (curRoleId.equals(String.valueOf(RolesCode.TEACHER))||curRoleId.equals(String.valueOf(RolesCode.HEAD_TEACHER))) {
			holder.tvClassName.setText(score.getClassName());
		}
		view.setBackgroundResource(R.drawable.layout_selector_bg);
		return view;
	}

	private class ViewHolder {
		private TextView tvContent;
		private TextView tvTime;
		private TextView tvClassName;
	}

}