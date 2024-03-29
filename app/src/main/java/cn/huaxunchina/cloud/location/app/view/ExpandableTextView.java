/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright 2014 Manabu Shimobe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.huaxunchina.cloud.location.app.view;

import cn.huaxunchina.cloud.app.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author gzc git hub原始地址 https://github.com/Manabu-GT/ExpandableTextView
 */
public class ExpandableTextView extends LinearLayout implements View.OnClickListener {

	private static final String TAG = "ExpandableTextView";

	// The default number of lines;
	private static final int MAX_COLLAPSED_LINES = 8;

	// The default animation duration
	private static final int DEFAULT_ANIM_DURATION = 300;

	// The default alpha value when the animation starts
	private static final float DEFAULT_ANIM_ALPHA_START = 0.7f;

	protected TextView mTv;

	protected TextView mFullText; // TextView to expand/collapse

	private View mExpandFootView;

	private boolean mRelayout;

	private boolean mCollapsed = true; // Show short version as default.

	private int mCollapsedHeight;

	private int mMaxTextHeight;

	private int mMaxCollapsedLines;

	private int mMarginBetweenTxtAndBottom;

	private Drawable mExpandDrawable;

	private Drawable mCollapseDrawable;

	private int mAnimationDuration;

	@SuppressWarnings("unused")
	private float mAnimAlphaStart;

	public static final int ClickAll = 0;
	public static final int ClickFooter = 1;

	// when in listview , use this map to save collapsed status
	private SparseBooleanArray mConvertTextCollapsedStatus;
	private int mPosition;

	private int mClickType;

	public ExpandableTextView(Context context) {
		super(context);
	}

	public ExpandableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	@Override
	public void onClick(View view) {
		if (mExpandFootView.getVisibility() != View.VISIBLE) {
			return;
		}

		mCollapsed = !mCollapsed;
		if (mConvertTextCollapsedStatus != null) {
			mConvertTextCollapsedStatus.put(mPosition, mCollapsed);
		}
		Log.i(TAG, " put postion " + mPosition + " " + mCollapsed + " this "
				+ this);
		mFullText.setText(mCollapsed ? "全文": "收起");

		Animation animation;

		Log.i(TAG, "click on position " + mPosition + " collapsed "
				+ mCollapsed);

		if (mCollapsed) {
			animation = new ExpandCollapseAnimation(this, getHeight(),
					mCollapsedHeight);
		} else {
			animation = new ExpandCollapseAnimation(this, getHeight(),
					getHeight() + mMaxTextHeight - mTv.getHeight());
		}

		animation.setFillAfter(true);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				clearAnimation();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});

		clearAnimation();
		startAnimation(animation);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		Log.d(TAG, " onMeasure ");
		// If no change, measure and return
		if (!mRelayout || getVisibility() == View.GONE) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			return;
		}
		mRelayout = false;

		mExpandFootView.setVisibility(View.GONE);
		mTv.setMaxLines(Integer.MAX_VALUE);

		// Measure
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// If the text fits in collapsed mode, we are done.
		if (mTv.getLineCount() <= mMaxCollapsedLines) {
			return;
		}

		// Saves the text height w/ max lines
		mMaxTextHeight = getTextViewRealHeight(mTv);
		Log.i(TAG, " mMaxTextHeight" + mMaxTextHeight);

		// Doesn't fit in collapsed mode. Collapse text view as needed. Show
		// button.
		if (mCollapsed) {
			mTv.setMaxLines(mMaxCollapsedLines);
		}
		// mFullText.setVisibility(View.VISIBLE);
		mExpandFootView.setVisibility(View.VISIBLE);

		// Re-measure with new setup
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (mCollapsed) {
			// Gets the margin between the TextView's bottom and the ViewGroup's
			// bottom
			mTv.post(new Runnable() {
				@Override
				public void run() {
					mMarginBetweenTxtAndBottom = getHeight() - mTv.getHeight();
				}
			});
			// Saves the collapsed height of this ViewGroup
			mCollapsedHeight = getMeasuredHeight();
		}

	}

	private void init(AttributeSet attrs) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.ExpandableTextView);
		mMaxCollapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_maxCollapsedLines,
				MAX_COLLAPSED_LINES);
		mAnimationDuration = typedArray.getInt(
				R.styleable.ExpandableTextView_animDuration,
				DEFAULT_ANIM_DURATION);
		mAnimAlphaStart = typedArray.getFloat(
				R.styleable.ExpandableTextView_animAlphaStart,
				DEFAULT_ANIM_ALPHA_START);
		mExpandDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_expandDrawable);
		mCollapseDrawable = typedArray
				.getDrawable(R.styleable.ExpandableTextView_collapseDrawable);
		mClickType = typedArray.getInt(
				R.styleable.ExpandableTextView_clickListenerType, ClickAll);

		if (mExpandDrawable == null) {
			mExpandDrawable = getResources().getDrawable(
					R.drawable.ic_expand_small_holo_light);
		}
		if (mCollapseDrawable == null) {
			mCollapseDrawable = getResources().getDrawable(R.drawable.ic_collapse_small_holo_light);
		}
		typedArray.recycle();
	}

	private static boolean isPostHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	private void findViews() {
		mTv = (TextView) findViewById(R.id.expandable_text);
		mFullText = (TextView) findViewById(R.id.expand_collapse);
		mFullText.setText(mCollapsed ? "全文": "收起");
		mExpandFootView = findViewById(R.id.expand_footer);

		if (mClickType == ClickAll) {
			mFullText.setOnClickListener(this);
			setOnClickListener(this);
			mTv.setOnClickListener(this);
			mExpandFootView.setOnClickListener(this);
		} else if (mClickType == ClickFooter) {
			mFullText.setOnClickListener(this);
			mTv.setClickable(false);
			setClickable(false);
			mExpandFootView.setOnClickListener(this);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private static void applyAlphaAnimation(View view, float alpha) {
		if (isPostHoneycomb()) {
			view.setAlpha(alpha);
		} else {
			AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
			// make it instant
			alphaAnimation.setDuration(0);
			alphaAnimation.setFillAfter(true);
			view.startAnimation(alphaAnimation);
		}
	}

	public void setText(String text) {
		mRelayout = true;
		if (mTv == null) {
			findViews();
		}
		mTv.setText(text);
		setVisibility(text.length() == 0 ? View.GONE : View.VISIBLE);
	}

	public void setConvertText(SparseBooleanArray convertStatus, int position, String text) {
		mConvertTextCollapsedStatus = convertStatus;
		boolean isCollapsed = mConvertTextCollapsedStatus.get(position, true);
		Log.i(TAG, "setConvertText is collapsed " + isCollapsed + " position" + position + " this " + this);
		mPosition = position;
		clearAnimation();
		mCollapsed = isCollapsed;
		if (mFullText != null) {
			mFullText.setText(mCollapsed ? "全文": "收起");
		}
		clearAnimation();
		if (mCollapsed) {
			if (mTv != null) {
				mTv.setMaxLines(mMaxCollapsedLines);
			}
		} else {
			if (mTv != null) {
				mTv.setMaxLines(Integer.MAX_VALUE);
			}
		}
		this.getLayoutParams().height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		setText(text);
		requestLayout();
	}

	public CharSequence getText() {
		if (mTv == null) {
			return "";
		}
		return mTv.getText();
	}

	protected class ExpandCollapseAnimation extends Animation {
		private final View mTargetView;
		private final int mStartHeight;
		private final int mEndHeight;

		public ExpandCollapseAnimation(View view, int startHeight, int endHeight) {
			mTargetView = view;
			mStartHeight = startHeight;
			mEndHeight = endHeight;

			setDuration(mAnimationDuration);
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			final int newHeight = (int) ((mEndHeight - mStartHeight)
					* interpolatedTime + mStartHeight);
			mTv.setMaxHeight(newHeight - mMarginBetweenTxtAndBottom);
			// applyAlphaAnimation(mTv, mAnimAlphaStart + interpolatedTime *
			// (1.0f - mAnimAlphaStart));
			mTargetView.getLayoutParams().height = newHeight;
			mTargetView.requestLayout();
		}

		@Override
		public void initialize(int width, int height, int parentWidth,
				int parentHeight) {
			super.initialize(width, height, parentWidth, parentHeight);
		}

		@Override
		public boolean willChangeBounds() {
			return true;
		}
	};

	private int getTextViewRealHeight(TextView pTextView) {
		Layout layout = pTextView.getLayout();
		int desired = layout.getLineTop(pTextView.getLineCount());
		int padding = pTextView.getCompoundPaddingTop()+ pTextView.getCompoundPaddingBottom();
		return desired + padding;
	}
}