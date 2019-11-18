package cn.huaxunchina.cloud.app.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.huaxunchina.cloud.app.model.todayread.TodayReadData;
import cn.huaxunchina.cloud.app.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class TodayReadAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private List<TodayReadData> itmes;
	private int size = 0;
	private TodayReadData data;
	private DisplayImageOptions mDisplayImageOptions;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	@SuppressWarnings("deprecation")
	public TodayReadAdapter(Context context, List<TodayReadData> itmes) {
		this.mContext = context;
		this.itmes = itmes;
		this.mInflater = LayoutInflater.from(mContext);
		mDisplayImageOptions = new DisplayImageOptions.Builder()
				.showImageOnFail(R.drawable.image_default)
				.imageScaleType(ImageScaleType.EXACTLY)
				.showImageForEmptyUri(R.drawable.image_default)
				.cacheInMemory(true).cacheOnDisc(true)
				.displayer(new FadeInBitmapDisplayer(300))
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY).build();

	}

	@Override
	public int getCount() {
		if (itmes != null) {
			size = itmes.size();
		}
		return size;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	public void addItmes(List<TodayReadData> data, boolean next) {
		if (next) {
			if (data != null) {
				itmes.addAll(data);
				notifyDataSetInvalidated();
			}
		} else {
			if (data != null) {
				this.itmes = data;
				notifyDataSetInvalidated();
			}
		}
	}

	public void setCollect(int position, boolean isCollect) {
		itmes.get(position).setCollect(isCollect);

	}

	public TodayReadData getItemTodayRead(int postion) {
		return itmes.get(postion);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public void removeLocalItems(TodayReadData data) {
		if (data.getReadId() != null) {
			int id = Integer.valueOf(data.getReadId()) - 1;
			itmes.remove(id);
			notifyDataSetInvalidated();
		}

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		data = itmes.get(position);
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.today_read_item, null);
			holder.read_item_bg = (ImageView) convertView.findViewById(R.id.read_item_bg);
			holder.read_title = (TextView) convertView.findViewById(R.id.read_title);
			holder.comment_text = (TextView) convertView.findViewById(R.id.comment_text);
			holder.today_sorce = (TextView) convertView.findViewById(R.id.today_sroce);
			holder.liner = (LinearLayout) convertView.findViewById(R.id.liner);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String img_url = data.getImg();
		if (img_url != null) {
			holder.liner.setBackgroundColor(mContext.getResources().getColor(R.color.alpha_transparent));
			holder.read_title.setTextColor(mContext.getResources().getColor(R.color.white));
			holder.today_sorce.setTextColor(mContext.getResources().getColor(R.color.white));
			holder.read_item_bg.setVisibility(View.VISIBLE);
			
			ImageLoader.getInstance().displayImage(img_url,holder.read_item_bg, mDisplayImageOptions,
					animateFirstListener);

		} else {
			holder.read_item_bg.setVisibility(View.GONE);
			holder.liner.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
			holder.read_title.setTextColor(mContext.getResources().getColor(R.color.leave_bg));
			holder.today_sorce.setTextColor(mContext.getResources().getColor(R.color.bar_botoom_text));
		}
		
		holder.read_title.setText(data.getTitle());
		holder.comment_text.setText(data.getContext());
		holder.today_sorce.setText(data.getOrigin());
		convertView.setBackgroundResource(R.drawable.layout_selector_bg);
		return convertView;
	}

	public final class ViewHolder {
		public ImageView read_item_bg; // 每列背景图
		public TextView read_title; // 每日一读主题
		public TextView comment_text; // 评论内容
		public TextView today_sorce;// 文章来源
		public LinearLayout liner;
	}

	/**
	 * 图片加载第一次显示监听器
	 * 
	 * @author Administrator
	 * 
	 */
	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {
		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				// 是否第一次显示
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					// 图片淡入效果
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	
	public static int dp2px(Context context, int dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

}
