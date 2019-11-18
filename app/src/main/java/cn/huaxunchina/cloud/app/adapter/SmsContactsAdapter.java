package cn.huaxunchina.cloud.app.adapter;

import java.util.List;

import cn.huaxunchina.cloud.app.application.ApplicationController;
import cn.huaxunchina.cloud.app.model.ContactsData;
import cn.huaxunchina.cloud.app.model.ContactsInfo;
import cn.huaxunchina.cloud.app.model.SendSmsItem;
import cn.huaxunchina.cloud.app.tools.SmsUtil;
import cn.huaxunchina.cloud.app.R;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class SmsContactsAdapter extends BaseAdapter implements SectionIndexer {
	
	private List<ContactsInfo> contactsInfos = null;
	private SendSmsItem sendSmsItem=null;
	private int curCount = 0;
	private int size = 0;
	private String names = "";
	private TextView select,selectNum;
	public SmsContactsAdapter(SendSmsItem data, TextView select,TextView selectNum) {
		this.sendSmsItem=data;
		this.contactsInfos = sendSmsItem.getGrupData();
		this.select = select;
		this.selectNum = selectNum;
		
		size = contactsInfos.size();
		for(int i=0;i<size;i++){
		if(contactsInfos.get(i).isCheck()){
			curCount++;
		}
			
		}
		setTitle();
		select.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			if(curCount==size){
			curCount=0;
			SmsUtil.setAllCk(contactsInfos, false);
			}else{
			curCount = size;
			SmsUtil.setAllCk(contactsInfos, true);
			}
			notifyDataSetInvalidated();
			setTitle();
		}
		});
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<ContactsInfo> list) {
		this.contactsInfos = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		size = contactsInfos.size();
		return size;
	}

	@Override
	public Object getItem(int position) {
		return contactsInfos.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public String getNams() {
		StringBuffer buffer = new StringBuffer();
		int size = contactsInfos.size();
		for (int i = 0; i < size; i++) {
			ContactsInfo info = contactsInfos.get(i);
			if (info.isCheck()) {
				buffer.append(info.getUserName() + ";");
			}
		}
		names = buffer.toString();
		return names;
	}

	 
	
	
	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		final ViewHolder viewHolder;
		final ContactsInfo info = contactsInfos.get(position);
		if (view == null) {
			view = View.inflate(ApplicationController.getContext(),R.layout.smscontacts_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.llLetter = (LinearLayout) view.findViewById(R.id.letter_layout);
			viewHolder.tvCatalog = (TextView) view.findViewById(R.id.contacts_catalog);
			viewHolder.tvContactsName = (TextView) view.findViewById(R.id.contacts_name);
			viewHolder.ckMsm = (CheckBox) view.findViewById(R.id.contacts_check);
			viewHolder.llContacts = (LinearLayout) view.findViewById(R.id.contacts_layout);
			view.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.llLetter.setVisibility(View.VISIBLE);
			viewHolder.tvCatalog.setText(info.getSortLetters());
		} else {
			viewHolder.llLetter.setVisibility(View.GONE);
		}
		viewHolder.tvContactsName.setText(info.getUserName());
		
		if (info.isCheck()) {
			viewHolder.ckMsm.setChecked(true);
		} else {
			viewHolder.ckMsm.setChecked(false);
		}
		viewHolder.ckMsm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				boolean isChecked = info.isCheck();
				if (isChecked) {
					curCount--;
					info.setCheck(false);
				} else {
					curCount ++;
					info.setCheck(true);
				}
				contactsInfos.set(position, info);
				notifyDataSetChanged();
				setTitle();
			}
		});
		return view;
	}

	
	private void setTitle(){
		if(curCount==size){
			select.setText("全不选");
		}else{
			select.setText("全选");
		}
		selectNum.setText("("+curCount+")人");
	}
	
	
	public ContactsData getContactsData() {
		ContactsData data = new ContactsData();
		data.setData(contactsInfos);
		return data;
	}
	
	
	public SendSmsItem getAdapterData(){
		sendSmsItem.setGrupData(contactsInfos);
		sendSmsItem.setCount(curCount);
		if(curCount==size){
		sendSmsItem.setCk(true);	
		}else{
		sendSmsItem.setCk(false);
		}
		return sendSmsItem;
	}

	static class ViewHolder {
		TextView tvCatalog;// 目录
		LinearLayout llLetter;// 个人信息栏
		TextView tvContactsName;// 姓名
		CheckBox ckMsm;// 是否选择
		LinearLayout llContacts;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	@Override
	public int getSectionForPosition(int position) {
		return contactsInfos.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = contactsInfos.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

}
