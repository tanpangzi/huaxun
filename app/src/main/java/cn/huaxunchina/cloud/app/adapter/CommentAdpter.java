package cn.huaxunchina.cloud.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.model.interaction.CommentContent;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.R;

public class CommentAdpter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private int size = 0;
	private List<CommentContent> replyList;
	private CommentContent data;

	public CommentAdpter(Context context, List<CommentContent> replyList) {
		this.mContext = context;
		this.replyList = replyList;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (replyList != null) {
			size = replyList.size();
		}
		return size;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	public void addItems(CommentContent info) {
		replyList.add(0,info);
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.comment_list_item, null);
			holder.comment_image = (ImageView) convertView.findViewById(R.id.comment_img);
			holder.comment_time = (TextView) convertView.findViewById(R.id.comment_time);
			holder.comment_content = (TextView) convertView.findViewById(R.id.comment_content);
			holder.comment_person=(TextView) convertView.findViewById(R.id.comment_person);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		data = replyList.get(position);
		String reply = data.getType();
		if (reply.equals("1")) {
			holder.comment_image.setImageResource(R.drawable.home_school_teacher);
		} else {
			holder.comment_image.setImageResource(R.drawable.home_school_praten);
		}
		holder.comment_time.setText(DateUtil.getDateDetail(data.getReplyTime()));
		holder.comment_content.setText(data.getContent().toString());
		holder.comment_person.setText(data.getReplyerName());
		return convertView;

	}

	public final class ViewHolder {
		public ImageView comment_image; // 评论对象图标
		public TextView comment_time; // 评论时间
		public TextView comment_content;// 评论内容
		private TextView comment_person;//评论人

	}
}
