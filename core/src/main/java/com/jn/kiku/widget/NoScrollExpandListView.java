package com.jn.kiku.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (scrollview嵌套expandlistview)
 * @create by: chenwei
 * @date 2017/2/27 11:10
 */
public class NoScrollExpandListView extends ExpandableListView {

    public NoScrollExpandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
