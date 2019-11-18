package cn.huaxunchina.cloud.app.view;

import cn.huaxunchina.cloud.app.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LoginClearEdText extends LinearLayout {
	private Resources res;
	private ImageView left_image;
	private EditText et;
	private ImageView del_image;
	private TextWatcher mWatcher;
	/**
	 * 返回输入框内文字
	 * @return
	 */
	public CharSequence getText() {
		return et.getText();
	}

	/**
	 * 设置输入框内文字
	 */
	public void setText(CharSequence mText) {
		this.et.setText(mText);
	}
	
	/**
	 * 返回输入框内hint文字
	 * @return
	 */
	public CharSequence getHint() {
		return et.getHint();
	}
	
	/**
	 * 设置输入框内hint文字
	 */
	public void setHint(CharSequence mText) {
		this.et.setHint(mText);
	}
	
	/**
	 * 只能输入数字
	 */
	public void setNumberType(){
	   et.setInputType(InputType.TYPE_CLASS_NUMBER);
	}
	
	/**
	 * 只能密码类型
	 */
	@SuppressLint("InlinedApi")
	public void setPassType(){
	   et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}
	
	public LoginClearEdText(Context context) {
		super(context);
	}

	public LoginClearEdText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint({ "NewApi", "Recycle" })
	public LoginClearEdText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		res = getResources();
		TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.EditTextWithClearBtn);
		Drawable leftImage = ta.getDrawable(R.styleable.EditTextWithClearBtn_imageleft);
		CharSequence text = ta.getText(R.styleable.EditTextWithClearBtn_text);
		CharSequence hint = ta.getText(R.styleable.EditTextWithClearBtn_hint);
		
		// 控件整个的宽高
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 45));
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);
		setAddStatesFromChildren(true);
		setBackgroundResource(R.drawable.login_ed);
		
		// 左边的图片
		left_image = new ImageView(context);
		left_image.setPadding(10, 5, 5, 5);
		left_image.setImageDrawable(leftImage);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		left_image.setLayoutParams(layoutParams);
		addView(left_image);

		// 中间的输入框
		et = new EditText(context);
		et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
		et.setTextSize(17);// sp
		et.setBackgroundColor(res.getColor(R.color.transparent));
		et.setTextColor(res.getColor(R.color.txt_gray));
		et.setSingleLine(true);
		et.setText(text);
		et.setHint(hint);
		et.setInputType(InputType.TYPE_CLASS_NUMBER);
		et.setHintTextColor(res.getColor(R.color.grey));
		et.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// 获取焦点时才显示clear按钮
				String value = et.getText().toString();
				if (hasFocus && value != null && value.length() > 0) {
					del_image.setVisibility(View.VISIBLE);

				} else {
					del_image.setVisibility(View.INVISIBLE);
				}
			}
		});
		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s != null && s.toString().length() > 0) {
					del_image.setVisibility(View.VISIBLE);
				} else {
					del_image.setVisibility(View.INVISIBLE);
				}
				
				if (mWatcher != null) {
					mWatcher.onTextChanged(s, start, before, count);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if (mWatcher != null) {
					mWatcher.beforeTextChanged(s, start, count, after);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (mWatcher != null) {
					mWatcher.afterTextChanged(s);
				}
			}
		});

		addView(et);

		// 右边的删除按钮
		del_image = new ImageView(context);
		del_image.setPadding(0, 15, 25, 15);
		del_image.setImageResource(R.drawable.icon_delete);
		del_image.setVisibility(View.INVISIBLE);
		LayoutParams layoutParams3 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		del_image.setLayoutParams(layoutParams3);
		del_image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (del_image.getVisibility() == View.VISIBLE) {
					et.setText("");
				}
			}
		});
		addView(del_image);
		ta.recycle();
	}
	
	public void setFilters(InputFilter[] filters) {
		et.setFilters(filters);
	}
	
	public void addTextChangedListener(TextWatcher watcher) {
		et.addTextChangedListener(watcher);
	}


}
