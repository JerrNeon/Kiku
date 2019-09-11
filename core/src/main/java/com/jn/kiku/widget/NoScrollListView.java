package com.jn.kiku.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (scrollview嵌套expandlistview)
 * @create by: chenwei
 * @date 2017/2/27 11:10
 */
public class NoScrollListView extends ListView {
	public NoScrollListView(Context context) {
		super(context);
	}

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollListView(Context context, AttributeSet attrs,
                            int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	/**
	 * 重写该方法，达到使ListView适应ScrollView的效果
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}