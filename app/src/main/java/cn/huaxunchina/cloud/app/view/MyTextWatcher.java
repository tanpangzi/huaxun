package cn.huaxunchina.cloud.app.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MyTextWatcher implements TextWatcher {
	private TextView tvCount;
	private EditText mEditText;
	private int editStart;
	private int editEnd;
	private int MAX_COUNT;

	public MyTextWatcher(EditText mEditText, TextView tvCount, int max_count) {
		this.mEditText = mEditText;
		this.MAX_COUNT = max_count;
		this.tvCount = tvCount;
	}

	public void afterTextChanged(Editable s) {
		editStart = mEditText.getSelectionStart();
		editEnd = mEditText.getSelectionEnd();
		// 先去掉监听器，否则会出现栈溢出
		mEditText.removeTextChangedListener(this);

		// 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度
		// 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
		while (calculateLength(s.toString()) > MAX_COUNT) { // 当输入字符个数超过限制的大小时，进行截断操作
			s.delete(editStart - 1, editEnd);
			editStart--;
			editEnd--;
		}
		mEditText.setText(s);
		mEditText.setSelection(editStart);

		// 恢复监听器
		mEditText.addTextChangedListener(this);

		setLeftCount();
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	/**
	 * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
	 * 
	 * @param c
	 * @return
	 */
	private long calculateLength(CharSequence c) {

		/*
		 * 
		 * double len = 0; for (int i = 0; i < c.length(); i++) { int tmp =
		 * (int) c.charAt(i); if (tmp > 0 && tmp < 127) { len += 0.5; } else {
		 * len++; } }
		 */
		int len = 0;
		for (int i = 0; i < c.length(); i++) {
			len++;
		}
		return len;
	}

	/**
	 * 刷新剩余输入字数,最大值新浪微博是140个字，人人网是200个字
	 */
	public void setLeftCount() {
		// tvCount.setText(String.valueOf((MAX_COUNT - getInputCount())));
		tvCount.setText(String.valueOf((getInputCount())) + "/" + MAX_COUNT);
	}

	/**
	 * 获取用户输入的分享内容字数
	 * 
	 * @return
	 */
	private long getInputCount() {
		return calculateLength(mEditText.getText().toString());
	}

}
