package com.jn.kiku.widget.tablayout;

import android.content.Context;
import com.google.android.material.tabs.TabLayout;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jn.common.util.DensityUtils;
import com.jn.kiku.utils.ScreenUtils;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (修改了TabLayout的子项宽度和paddingEnd)
 * @create by: chenwei
 * @date 2017/6/12 14:15
 */
public class STabLayout extends TabLayout {

    private Context mContext;

    public STabLayout(Context context) {
        this(context, null);
    }

    public STabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public STabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    public void addTab(Tab tab, int position, boolean setSelected) {
        super.addTab(tab, position, setSelected);
        setWidth(tab);
    }

    @Override
    public void addTab(Tab tab, boolean setSelected) {
        super.addTab(tab, setSelected);
        setWidth(tab);
    }

    /**
     * 设置每个Tab的宽度
     *
     * @param tab
     */
    private void setWidth(Tab tab) {
        if (tab.getCustomView() == null) {
            ViewGroup tabGroup = (ViewGroup) getChildAt(0);
            ViewGroup tabContainer = (ViewGroup) tabGroup.getChildAt(tab.getPosition());
            View parentView = (View) tabContainer.getParent();
            ViewCompat.setPaddingRelative(parentView, 0, 0, DensityUtils.dp2px(mContext, 26), 0);//为TabLayout添加paddingEnd
            TextView textView = (TextView) tabContainer.getChildAt(1);
            textView.setMinimumWidth((int) (ScreenUtils.getScreenWidth(mContext) * 0.15));//设置每一个tab的宽度
        } else {
            View customView = tab.getCustomView();
            View parentView = (View) customView.getParent();
            ViewCompat.setPaddingRelative(parentView, 0, 0, DensityUtils.dp2px(mContext, 26), 0);//为TabLayout添加paddingEnd
            customView.setMinimumWidth((int) (ScreenUtils.getScreenWidth(mContext) * 0.15));//设置每一个tab的宽度
        }
    }
}
