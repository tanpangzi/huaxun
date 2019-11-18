package cn.huaxunchina.cloud.app.tools;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.activity.BaseActivity;
import cn.huaxunchina.cloud.app.common.Constant.HandlerCode;
import cn.huaxunchina.cloud.app.imp.ListDataCallBack;
import cn.huaxunchina.cloud.app.view.LoadingDialog;

/**
 * listView列表数据适配封装数据
 * 
 * @author zoupeng@huaxunchina.cn
 * 
 *         2014-12-11 下午4:53:00
 */
public class MyListAdpterUtil extends BaseActivity {
	private PullToRefreshListView listView;
	private ListDataCallBack handlerOnCallBack;
	private LoadingDialog loadingDialog;
	private Context mcontext;

	/**
	 * 
	 * @param context
	 * @param listView
	 * @param handlerOnCallBack
	 * @param loadingDialog
	 */
	public MyListAdpterUtil(Context context, PullToRefreshListView listView,
			LoadingDialog loadingDialog, ListDataCallBack handlerOnCallBack) {
		this.mcontext = context;
		this.listView = listView;
		this.loadingDialog = loadingDialog;
		this.handlerOnCallBack = handlerOnCallBack;

	}

	public MyListAdpterUtil() {
	}

	/**
	 * 获取handler实例
	 * 
	 * @return
	 */
	public Handler getHandler() {
		return myHandler;
	}

	@SuppressLint({ "HandlerLeak", "ShowToast" })
	private Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (loadingDialog != null) {
				loadingDialog.dismiss();
			}
			switch (msg.what) {
			case HandlerCode.HANDLER_SUCCESS:
				listView.onRefreshComplete();
				handlerOnCallBack.onCallBack(msg);
				break;
			case HandlerCode.HANDLER_NET:
				listView.onRefreshComplete();
				Utils.netWorkToast(); // 请求网络异常
				break;
			case HandlerCode.HANDLER_ERROR:// 失败
				listView.onRefreshComplete();
				Toast.makeText(mcontext, "请求失败", 0).show();
				break;
			case HandlerCode.HANDLER_LASTPAGE:
				listView.onRefreshComplete();
				Toast.makeText(mcontext, "已是最后一页", 0).show();
				listView.setMode(Mode.PULL_FROM_START);
				break;
			case HandlerCode.HANDLER_TIME_OUT:// 超时
				Utils.netWorkToast();
				listView.setMode(Mode.DISABLED);
				listView.onRefreshComplete();
				break;
			case HandlerCode.COOKIESTORE_SUCCESS: // COOK失效
				Toast.makeText(mcontext, "已断开,请重新登录", 0).show();
				break;
			}
		}
	};

}
