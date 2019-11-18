package cn.huaxunchina.cloud.app.adapter;

import java.util.ArrayList;
import java.util.List;
import cn.huaxunchina.cloud.app.activity.contacts.SmsContent;
import cn.huaxunchina.cloud.app.model.ContactsInfo;
import cn.huaxunchina.cloud.app.model.SendSmsData;
import cn.huaxunchina.cloud.app.tools.SmsUtil;
import cn.huaxunchina.cloud.app.view.ContactsDialog;
import cn.huaxunchina.cloud.app.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class ContactsAdapter extends BaseAdapter implements SectionIndexer {
	private List<ContactsInfo> list = null;
	private Activity activity;

	public ContactsAdapter(Activity activity, List<ContactsInfo> list) {
		this.activity = activity;
		this.list = list;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<ContactsInfo> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		int count = 0;
		if (list != null) {
			count = list.size();
		}
		return count;
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final ContactsInfo info = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(activity).inflate(R.layout.contacts_list_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.llLetter = (LinearLayout) view.findViewById(R.id.letter_layout);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.tvCall = (TextView) view.findViewById(R.id.contacts_call);
			viewHolder.tvSms = (TextView) view.findViewById(R.id.contacts_sms);
			viewHolder.tvHead = (TextView) view.findViewById(R.id.contacts_head);
			viewHolder.tvPhone = (TextView) view.findViewById(R.id.contacts_phone);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.llLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(info.getSortLetters());
		} else {
			viewHolder.llLetter.setVisibility(View.GONE);
		}

		viewHolder.tvTitle.setText(info.getUserName());
		viewHolder.tvPhone.setText(info.getLinkPhone());
		if(info.isActive()){
			viewHolder.tvHead.setBackgroundResource(R.drawable.contacts_head1);
		}else{
			viewHolder.tvHead.setBackgroundResource(R.drawable.contacts_head2);
		}
		 

		viewHolder.tvCall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 final String tel = info.getLinkPhone();
				String name=info.getUserName();
				/*Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ tel));*/
				// 通知activtity处理传入的call服务
				//Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
                //通知activtity处理传入的call服务
				//activity.startActivity(intent);
				//new ContactsDialog1(activity,tel,name).show();
				
				ContactsDialog dialog = new ContactsDialog(activity);
				dialog.show();
				dialog.setTitle(name);
				dialog.setMessage(tel);
				dialog.setNegativeButton("取消");
				dialog.setPositiveButton("呼叫");
				dialog.setOkOnClickListener(
			    new ContactsDialog.OnClickListener(){
				@Override
				public void onClick() {
				String tel1 = tel;
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel1));
	            //通知activtity处理传入的call服务
				activity.startActivity(intent);
				}});
			}
		});
		viewHolder.tvSms.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(info.isActive()){
				info.setCheck(true);
				List<ContactsInfo> grupData = new ArrayList<ContactsInfo>();
				grupData.add(info);
				SendSmsData data = SmsUtil.creat(0, grupData);
				Intent intent = new Intent(activity, SmsContent.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", data);
				intent.putExtras(bundle);
				activity.startActivity(intent);
				}else{
				Toast.makeText(activity, "未启用短信接收功能", Toast.LENGTH_LONG).show();
				}
			}
		});

		return view;

	}

	final static class ViewHolder {
		TextView tvLetter;
		LinearLayout llLetter;
		TextView tvTitle;
		TextView tvCall;
		TextView tvSms;
		TextView tvHead;
		TextView tvPhone;

	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@SuppressLint("DefaultLocale")
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}