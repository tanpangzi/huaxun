package cn.huaxunchina.cloud.location.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.R;

public class LocSetListAdpter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private int size = 0;
	private String[] detial_key = {};
	private int ImageResoure[];
	private final int TYPE_COUNT = 2;
	private final int FIRST_TYPE = 0;
	private final int OTHERS_TYPE = 1;
	private int currentType;

	public LocSetListAdpter(Context context, String[] detail_key,int ImageResoure[]) {
		this.mContext = context;
		this.detial_key = detail_key;
		this.ImageResoure = ImageResoure;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (detial_key != null) {
			size = detial_key.length;
		}
		return size;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	@Override
	public int getItemViewType(int position) {
		if (detial_key[position].length() == 1) {
			return OTHERS_TYPE;
		} else {
			return FIRST_TYPE;
		}
	}

	@Override
	public boolean isEnabled(int position) {
		return detial_key[position].length() != 1;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 View fristConvertView = null;
		 View otherConvertView = null;
		currentType = getItemViewType(position);
		if (currentType == FIRST_TYPE) {
			viewHolder_set viewHolder=null;
			if (convertView == null) {
				viewHolder = new viewHolder_set();
				fristConvertView = mInflater.inflate(R.layout.loc_setting_list_item,null);
				viewHolder.loc_setting_title=(TextView) fristConvertView.findViewById(R.id.loc_setting_title);
				viewHolder.loc_setting_image=(ImageView) fristConvertView.findViewById(R.id.loc_setting_image);
				fristConvertView.setTag(viewHolder);
		        convertView = fristConvertView;
		      } else {
		    	  viewHolder = (viewHolder_set) convertView.getTag();
		      }
			
			if (detial_key[position].length() != 1) {
				viewHolder.loc_setting_title.setText(detial_key[position]);
				viewHolder.loc_setting_image.setImageResource(ImageResoure[position]);
			}
			 convertView.setBackgroundResource(R.drawable.layout_selector_bg);
			  
		}else{
			viewHolder_space space_viewHolder=null;
			if (convertView == null) {
				space_viewHolder = new viewHolder_space();
				otherConvertView = mInflater.inflate(R.layout.loc_setting_space_list_item,null);
				space_viewHolder.loc_setting_space_title=(TextView) otherConvertView.findViewById(R.id.space_txt);
				otherConvertView.setTag(space_viewHolder);
		        convertView = otherConvertView;
		      } else {
		    	  space_viewHolder = (viewHolder_space) convertView.getTag();
		      }
			space_viewHolder.loc_setting_space_title.setText("");
		} 
		return convertView;
	}
	
	class viewHolder_set{
		private TextView loc_setting_title; //主题
		private ImageView loc_setting_image;//图标
		
	}
	class viewHolder_space{
		private TextView loc_setting_space_title; //主题
		
	}
}
