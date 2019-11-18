package cn.huaxunchina.cloud.app.tools;

import cn.huaxunchina.cloud.app.model.PageInfo;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

/**
 * 接收Handler成功后处理list中Adpter的数据
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-15 下午12:09:50
 */
public class HandSucessAdpter {
 
   public static void initPageInfo(PullToRefreshListView listView, PageInfo pageInfo,int curPage2, int totalCount){
		pageInfo.setCurPage(curPage2);
		pageInfo.setNumCount(totalCount);
		int pageCount = pageInfo.getPageCount();
		if (pageCount > 1) {
			listView.setMode(Mode.BOTH);
		} else {
			listView.setMode(Mode.PULL_FROM_START);
		}
   }
}
