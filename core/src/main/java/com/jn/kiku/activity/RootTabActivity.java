package com.jn.kiku.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jn.kiku.R;
import com.jn.kiku.common.api.ITabLayoutView;
import com.jn.kiku.utils.ScreenUtils;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：RootTabActivity
 */
public abstract class RootTabActivity extends RootTbActivity implements ITabLayoutView {

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
        //set TabLayout IndicatorFullWidth
        mTabLayout.setTabIndicatorFullWidth(isTabIndicatorFullWidth());
    }

    /**
     * set ViewPager load number（don't need use this function if not use lazyLod fragment）
     */
    @Override
    public void setOffscreenPageLimit() {
        if (fragments != null)
            mViewPager.setOffscreenPageLimit(fragments.length);
    }

    @Override
    public boolean isTabIndicatorFullWidth() {
        return true;
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

        BaseTabFragmentAdapter(FragmentManager fm) {
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
