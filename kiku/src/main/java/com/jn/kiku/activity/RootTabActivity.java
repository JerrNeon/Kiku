package com.jn.kiku.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.jn.kiku.R;
import com.jn.kiku.common.api.ITabLayouView;
import com.jn.kiku.utils.ScreenUtils;
import com.jn.kiku.utils.ViewsUtils;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RootTabActivity)
 * @create by: chenwei
 * @date 2018/5/16 16:03
 */
public abstract class RootTabActivity extends RootTbActivity implements ITabLayouView {

    protected Fragment[] fragments = null;
    protected String[] titles = null;

    protected TabLayout mTabLayout = null;
    protected ViewPager mViewPager = null;

    protected abstract Fragment[] getFragments();

    protected abstract String[] getTitles();

    @Override
    public int getLayoutResourceId() {
        return R.layout.common_tab_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabView();
        setTabLayoutAttribute();
    }

    @Override
    protected void setTitleText(String titleText) {
        super.setTitleText(titleText, false);
    }

    /**
     * init TabLayout
     */
    @Override
    public void initTabView() {
        fragments = getFragments();
        titles = getTitles();

        mTabLayout = findViewById(R.id.tabLayout_RootTab);
        mViewPager = findViewById(R.id.viewpager_RootTab);
        mViewPager.setAdapter(new BaseTabFragmentAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * set TabLayout style
     */
    @Override
    public void setTabLayoutAttribute() {
        //indicator color
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(mContext, getTabIndicatorColorId()));
        //text color
        mTabLayout.setTabTextColors(ContextCompat.getColor(mContext, getTabNormalTextColorId()), ContextCompat.getColor(mContext, getTabSelectedTextColorId()));
        //set TabLayout item marginLeft and marginRight
        setTabLayoutIndicatorMargin(getTabItemMargin(), getTabItemMargin());
    }

    /**
     * set ViewPager load number（don't need use this function if not use lazyLod fragment）
     */
    @Override
    public void setOffscreenPageLimit() {
        if (fragments != null)
            mViewPager.setOffscreenPageLimit(fragments.length);
    }

    /**
     * set TabLayout item marginLeft and marginRight
     *
     * @param marginLeft  marginLeft
     * @param marginRight marginRight
     */
    @Override
    public void setTabLayoutIndicatorMargin(int marginLeft, int marginRight) {
        ViewsUtils.setTabLayoutIndicatorMargin(mContext, mTabLayout, marginLeft, marginRight);
    }

    @Override
    public int getTabIndicatorColorId() {
        return R.color.colorPrimaryDark;
    }

    @Override
    public int getTabNormalTextColorId() {
        return R.color.colorPrimary;
    }

    @Override
    public int getTabSelectedTextColorId() {
        return R.color.colorPrimaryDark;
    }

    @Override
    public int getTabItemMargin() {
        return ScreenUtils.getScreenWidth(mContext) / fragments.length / 10;//item width's percent one of 10
    }

    private class BaseTabFragmentAdapter extends FragmentPagerAdapter {

        public BaseTabFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

    }

}
