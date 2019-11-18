package cn.huaxunchina.cloud.app.tools;

import android.util.SparseArray;
import android.view.View;

/**
 * 构造adpter数据视图
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-11-20 下午3:51:32
 */

public class ViewHolder {

	private ViewHolder() {
	}

	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

}
