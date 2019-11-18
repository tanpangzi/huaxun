package cn.huaxunchina.cloud.location.app.activity.stt.crawl;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.tools.Utils;
import cn.huaxunchina.cloud.app.view.LoadingDialog;
import cn.huaxunchina.cloud.app.view.MyBackView;
import cn.huaxunchina.cloud.location.app.model.post.Circle;
import cn.huaxunchina.cloud.location.app.tools.TimeUtil;

/**
 * 电子围栏检索地址
 * TODO(描述) 
 * @author jiangyizhao@huaxunchina.cn
 * @date 2015年1月21日 下午5:32:14 
 *
 */
public class PoiActivity extends Activity  implements OnClickListener{
	
	private ListView poiList;
	private EditText poiEd;
	private PoiAdapter adapter;
	private PoiSearch poiSearch;
	private List<PoiInfo> infos = new ArrayList<PoiInfo>();
	private String curr_city="";
	private String keyword = null;
	private LoadingDialog loadingDialog;
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loc_poi);
		curr_city=this.getIntent().getStringExtra("curr_city");
		initView();
	}
	
	 

	public void initView(){
		MyBackView back = (MyBackView)findViewById(R.id.back);
		back.setBackText("电子围栏");
		back.setAddActivty(this);
		findViewById(R.id.poi_btn).setOnClickListener(this);
		loadingDialog = new LoadingDialog(this);
		poiList=(ListView)findViewById(R.id.poi_listview);
		poiList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int id,long arg3) {
				// TODO Auto-generated method stub
				 PoiInfo info = infos.get(id);
				 Intent intent = new Intent(PoiActivity.this, FencingActivity.class);
				 double lat = info.location.latitude;
				 double lng = info.location.longitude;
				 Circle circle = new Circle();
				 circle.setPositionLat(lat);
				 circle.setPositionLng(lng);
				 circle.setPointAddress(info.address);
				 circle.setPositionName(info.name);
				 circle.setPositionAlarm(true);
				 circle.setPositionRadius(500);
				 circle.setRepeatDay(127);
				 circle.setCircle_id(0);
				 circle.setNoticeType(2);
				 circle.setStartTime(TimeUtil.getCurYMothTime1());
				  
				 circle.setEndTime(TimeUtil.getCurYMothTime2()+"2359");
				 
				 System.out.println("====时间1:"+circle.getStartTime());
				 System.out.println("=====时间2:"+circle.getEndTime());
				 
				 Bundle bundle = new Bundle();
				 bundle.putSerializable("data", circle);
				 intent.putExtras(bundle);
				 setResult(ResultCode.FEN_CODE, intent);
				 finish();
				
			}});
		poiEd=(EditText)findViewById(R.id.poi_ed);
		adapter = new PoiAdapter(infos);
		poiList.setAdapter(adapter);
		poiSearch = PoiSearch.newInstance();
		OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){  
			@Override
			public void onGetPoiDetailResult(PoiDetailResult result) {
				  //获取Place详情页检索结果  
				 if (result.error != SearchResult.ERRORNO.NO_ERROR) {
				        //详情检索失败
				        // result.error请参考SearchResult.ERRORNO 
				    } 
				    else{
				        //检索成功

				    }
			}
			@Override
			public void onGetPoiResult(PoiResult poiResult) {
				//获取POI检索结果  
				loadingDialog.dismiss();
				List<PoiInfo> results = poiResult.getAllPoi();
				if(results!=null){
				infos=results;
				adapter.setItems(infos);
				}else{
				infos = new ArrayList<PoiInfo>();
				adapter.setItems(infos);
				Toast.makeText(PoiActivity.this, "找不到相关内容", Toast.LENGTH_LONG).show();	
				}
			}  
		};
		poiSearch.setOnGetPoiSearchResultListener(poiListener);
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.poi_btn:
			if (!Utils.isNetworkConn()) {
				Toast.makeText(PoiActivity.this, "请检查网络", Toast.LENGTH_LONG).show();	
				return;
			}else{
			loadingDialog.show();
			keyword=poiEd.getText().toString().trim();
			if(!TextUtils.isEmpty(keyword)){
				poiSearch.searchInCity((new PoiCitySearchOption())  
			    .city(curr_city)  
			    .keyword(keyword) 
			    .pageCapacity(20));
			}else{
				Toast.makeText(this, "请输入关键词", Toast.LENGTH_LONG).show();
			}
			}
			break;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		poiSearch.destroy();
	}
	
	
	private class PoiAdapter extends BaseAdapter{
		private List<PoiInfo> infos;
		public PoiAdapter(List<PoiInfo> infos){
			this.infos=infos;
		}
		
		public void setItems(List<PoiInfo> infos){
			this.infos=infos;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			int count = 0;
			if(infos!=null){
			count = infos.size();
			}
			return count;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg1, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (null == convertView) {
				holder = new ViewHolder();
				convertView = View.inflate(PoiActivity.this, R.layout.loc_poi_item, null);
				holder.addressName=(TextView)convertView.findViewById(R.id.poi_address_name);
				holder.addressInfo=(TextView)convertView.findViewById(R.id.poi_address_info);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			PoiInfo poiInfo = infos.get(arg1);
			holder.addressName.setText(poiInfo.name);
			holder.addressInfo.setText(poiInfo.address);
			return convertView;
		}
		
	}
	
	public final class ViewHolder {
		public TextView addressName; // 地址名称
		public TextView addressInfo;// 地址详细

	}

}
