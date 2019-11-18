package cn.huaxunchina.cloud.app.adapter;

import java.util.List;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.model.interaction.HomeSchoolListData;
import cn.huaxunchina.cloud.app.tools.DateUtil;
import cn.huaxunchina.cloud.app.R;

public class HomeSchoolActivitiesAdpter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<HomeSchoolListData> itmes;
	private HomeSchoolListData data;
	private int size;
	private DisplayImageOptions mDisplayImageOptions;

	@SuppressWarnings("deprecation")
	public HomeSchoolActivitiesAdpter(List<HomeSchoolListData> itmes) {
		this.mContext = ApplicationController.getContext();
		this.itmes = itmes;
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
		size = 0;
		if (itmes != null || !itmes.equals("")) {
			size = itmes.size();
		}
		return size;
	}

	@Override
	public Object getItem(int position) {

		return position;
	}
	
	public HomeSchoolListData getItemData(int position){
		return itmes.get(position);
	}

	
	
	
	public void addItems(List<HomeSchoolListData> data,boolean next) {
		if (next) {
		itmes.addAll(data);
		notifyDataSetInvalidated();
		} else {
		this.itmes=data;
		notifyDataSetInvalidated();
		}

	}
	
	public void addLocalItems(HomeSchoolListData data) {
		itmes.add(0,data);
		notifyDataSetChanged();
	}
	
	
	@Override
	public long getItemId(int position) {

		return position;
	}
	
	public List<HomeSchoolListData> getItemsValue() {
		return itmes;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		data = itmes.get(position);
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.home_school_list_item,null);
			holder.home_school_image1 = (ImageView) convertView.findViewById(R.id.home_school_image);
			holder.home_school_image2=(ImageView) convertView.findViewById(R.id.home_school_image2);
			holder.interaction_title = (TextView) convertView.findViewById(R.id.interaction_title);
			holder.interaction_content = (TextView) convertView.findViewById(R.id.home_school_content);
			holder.interaction_time = (TextView) convertView.findViewById(R.id.home_school_time);
			holder.interaction_image=(ImageView) convertView.findViewById(R.id.image_type);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		List<String> imgList = data.getImgList();
		if(imgList!=null&&imgList.size()>0){
			holder.home_school_image1.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(imgList.get(0), holder.home_school_image1, mDisplayImageOptions);
			if(imgList.size()>1){
				holder.home_school_image2.setVisibility(View.VISIBLE);
				ImageLoader.getInstance().displayImage(imgList.get(1), holder.home_school_image2, mDisplayImageOptions);
			}
		}else{
			holder.home_school_image1.setVisibility(View.GONE);
			holder.home_school_image2.setVisibility(View.GONE);
		}
		holder.interaction_title.setText(data.getTitle().toString());
		holder.interaction_content.setText(data.getContent().toString());
		holder.interaction_time.setText(DateUtil.getDateDetail(data.getPublishTime().toString()));
		String private_type=data.getIsPrivate().toString();
		convertView.setBackgroundResource(R.drawable.layout_selector_bg);
		if(private_type!=null&&private_type.equals("2")){
			holder.interaction_image.setVisibility(View.VISIBLE);
		}else{
			holder.interaction_image.setVisibility(View.INVISIBLE);
		}
		return convertView;

	}

	public final class ViewHolder {
		public ImageView home_school_image1; // 互动列表图片
		public ImageView home_school_image2;//互动列表图片2
		public TextView interaction_title; // 互动主题
		public TextView interaction_content; // 互动内容
		public TextView interaction_time;// 互动时间
		private ImageView interaction_image; //是否为私密

	}
}
