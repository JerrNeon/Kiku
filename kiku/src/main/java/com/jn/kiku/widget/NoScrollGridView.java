package com.jn.kiku.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (scrollview嵌套expandlistview)
 * @create by: chenwei
 * @date 2017/2/27 11:10
 */
public class NoScrollGridView extends GridView {

	public NoScrollGridView(Context context) {
		super(context);

	}

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	/*
	 * @Override protected void onMeasure(int widthMeasureSpec, int
	 * heightMeasureSpec) { super.onMeasure(widthMeasureSpec,
	 * heightMeasureSpec);
	 * 
	 * Layout layout = getLayout(); if (layout != null) { int height = (int)
	 * FloatMath.ceil(getMaxLineHeight(this.getText() .toString())) +
	 * getCompoundPaddingTop() + getCompoundPaddingBottom(); int width =
	 * getMeasuredWidth(); setMeasuredDimension(width, height); } }
	 * 
	 * private float getMaxLineHeight(String str) { float height = 0.0f; float
	 * screenW = ((Activity) context).getWindowManager()
	 * .getDefaultDisplay().getWidth(); float paddingLeft = ((LinearLayout)
	 * this.getParent()).getPaddingLeft(); float paddingReft = ((LinearLayout)
	 * this.getParent()).getPaddingRight(); //
	 * 这里具体this.getPaint()要注意使用，要看你的TextView在什么位置
	 * ，这个是拿TextView父控件的Padding的，为了更准确的算出换行 int line = (int)
	 * Math.ceil((this.getPaint().measureText(str) / (screenW - paddingLeft -
	 * paddingReft))); height = (this.getPaint().getFontMetrics().descent -
	 * this.getPaint() .getFontMetrics().ascent) * line; return height; }
	 */

}
