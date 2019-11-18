package cn.huaxunchina.cloud.app.adapter;

import cn.huaxunchina.cloud.app.R;
import cn.huaxunchina.cloud.app.activity.contacts.SmsContacts;
import cn.huaxunchina.cloud.app.common.Constant.ResultCode;
import cn.huaxunchina.cloud.app.model.SendSmsData;
import cn.huaxunchina.cloud.app.model.SendSmsItem;
import cn.huaxunchina.cloud.app.tools.SmsUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GroupSelectListAdpter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private int size = 0;
	private SendSmsItem[] items;
	private SendSmsData  sendSmsData;
	private TextView title;
	private int curCount = 0;
	

	public GroupSelectListAdpter(Context context,SendSmsData  data,TextView title) {
		this.mContext = context;
		this.sendSmsData=data;
		this.items = data.getData();
		this.mInflater = LayoutInflater.from(mContext);
		this.title=title;
		size = items.length;
		for (int i = 0; i < size; i++) {
			if(items[i].isCk()){
				curCount++;
			}
		}
		setTitle();
		title.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if(curCount==size){
			curCount=0;
			SmsUtil.setAllCk(sendSmsData, false);
			}else{
			curCount = size;
			SmsUtil.setAllCk(sendSmsData, true);
			}
			notifyDataSetInvalidated();
			setTitle();
			
		}
		});
	}

	@Override
	public int getCount() {
		if (items != null) {
			size = items.length;
		}
		return size;
	}
	
	public SendSmsData getAdpterData(){
		this.sendSmsData.setData(items);
		return sendSmsData;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
 
	public void setData(SendSmsData  data){
		this.items = data.getData();
		notifyDataSetInvalidated();
	}
	
  
	
 

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		 ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.group_select_list_item,null);
			holder.select_txt = (TextView) convertView.findViewById(R.id.group_list_item_txt);
			holder.groupCk = (CheckBox) convertView.findViewById(R.id.group_select_check);
			holder.groupCk.setClickable(true);
			holder.num_txt = (TextView) convertView.findViewById(R.id.num_txt);
			holder.group = (RelativeLayout) convertView.findViewById(R.id.group_list_relative);
			holder.group.setTag(position);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		 
		
		SendSmsItem info = items[position];
		holder.select_txt.setText(info.getGrupName());
        holder.num_txt.setText(info.getCountStr());
		if (info.isCk()){
		holder.groupCk.setChecked(true);
		} else {
		holder.groupCk.setChecked(false);
		}
		onCheckedChangeListener(holder.groupCk,info);
		holder.group.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SendSmsItem data = items[position];
				Intent intent=new Intent(mContext, SmsContacts.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", data);
				intent.putExtras(bundle);
				((Activity) mContext).startActivityForResult(intent, ResultCode.SMSGROUP_CODE);
				//Toast.makeText(mContext, info.getId()+"", Toast.LENGTH_LONG).show();
			}
		});
		return convertView;
	}
	
	
	private void onCheckedChangeListener(CheckBox checkBox,final SendSmsItem info){
		checkBox.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SendSmsItem item_data = items[info.getId()];
				boolean isChecked = item_data.isCk();
				if (isChecked) {
					item_data.setCk(false);
					item_data.setCount(0);
					curCount--;
				} else {
					curCount++;
					item_data.setCk(true);
					item_data.setCount(item_data.getGrupData().size());
					
				}
				item_data=SmsUtil.setChecked(item_data, !isChecked);
				items[info.getId()]=item_data;
				notifyDataSetChanged();
				setTitle();
			}});
	}
	
	
	private void setTitle(){
		if(curCount==size){
			title.setText("全不选");
		}else{
			title.setText("全选");
		}
	}
	 

	public final class ViewHolder {
		public CheckBox groupCk; // 群组选择ck
		public TextView select_txt; // 组群对象选择
		public TextView num_txt; // 群组成员数量
		private RelativeLayout group;
	}

	 
}
