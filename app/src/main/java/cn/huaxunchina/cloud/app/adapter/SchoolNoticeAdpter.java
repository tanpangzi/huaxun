package cn.huaxunchina.cloud.app.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.model.notice.NoticeData;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class SchoolNoticeAdpter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context mContext;
	private List<NoticeData> items;
	private int size = 0;
	private DisplayImageOptions mDisplayImageOptions;

	@SuppressWarnings("deprecation")
	public SchoolNoticeAdpter(Context context, List<NoticeData> items) {
		this.mContext = context;
		this.items = items;
		this.mInflater = LayoutInflater.from(mContext);
		mDisplayImageOptions = new DisplayImageOptions.Builder() 
        .showStubImage(R.drawable.notice_default) 
        .showImageOnFail(R.drawable.notice_default) 
        .imageScaleType(ImageScaleType.EXACTLY) 
        .showImageForEmptyUri(R.drawable.notice_default).cacheInMemory(true) 
        .cacheOnDisc(true).displayer(new FadeInBitmapDisplayer(300)) 
        .imageScaleType(ImageScaleType.EXACTLY).build();
	}

	@Override
	public int getCount() {
		if (items != null) {
			size = items.size();
		}
		return size;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	public void addItems(List<NoticeData> data, boolean next) {
		if (next) {
			if (data != null) {
				items.addAll(data);
				notifyDataSetInvalidated();
			}
		} else {
			if (data != null) {
				this.items=data;
				notifyDataSetInvalidated();
			}
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public NoticeData getItemNotice(int postion) {
		return items.get(postion);
	}
	
	public void addLocalItems(NoticeData data) {
		items.add(0,data);
		notifyDataSetChanged();
	}
	
	public List<NoticeData> getItemsValue() {
		return items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		NoticeData notice = items.get(position);
		String[] url = notice.getImgList();
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.notice_list_item, null);
			holder.notice_image = (ImageView) convertView
					.findViewById(R.id.notice_image);
			holder.notice_image2 = (ImageView) convertView
					.findViewById(R.id.notice_image2);
			holder.notice_title = (TextView) convertView
					.findViewById(R.id.notice_title);
			holder.notice_content = (TextView) convertView
					.findViewById(R.id.notice_content);
			holder.notice_time = (TextView) convertView
					.findViewById(R.id.notice_time);
			holder.sumbit_txt = (TextView) convertView
					.findViewById(R.id.sumbit_txt);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (url!=null&&url.length>0) {
			holder.notice_image.setVisibility(View.VISIBLE);
	    	ImageLoader.getInstance().displayImage(url[0], holder.notice_image, mDisplayImageOptions);
	    	if(url.length==2){
	    		holder.notice_image2.setVisibility(View.VISIBLE);
		    	ImageLoader.getInstance().displayImage(url[1], holder.notice_image2, mDisplayImageOptions);	
	    	}
		}else{
			holder.notice_image2.setVisibility(View.GONE);
			holder.notice_image.setVisibility(View.GONE);
			
		}
		convertView.setBackgroundResource(R.drawable.layout_selector_bg);
		holder.notice_title.setText(notice.getTitle());
		holder.notice_content.setText(notice.getContent());
		holder.notice_time.setText(DateUtil.getDateDetail(notice.getPublishTime().toString()));
		holder.sumbit_txt.setText("发布人:"+notice.getPublisher());
		return convertView;
	}
	

	public final class ViewHolder {
		public ImageView notice_image; // 公告图片1
		public ImageView notice_image2; // 公告图片2
		public TextView notice_title; // 公告主题
		public TextView notice_content; // 公告内容
		public TextView notice_time; // 公告发布时间
		public TextView notice_edit_person; // 公告发布人
		public TextView sumbit_txt;
	}
}
