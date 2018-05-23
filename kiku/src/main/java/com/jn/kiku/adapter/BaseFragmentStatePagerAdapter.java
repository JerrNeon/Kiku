package com.jn.kiku.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (BaseFragmentStatePagerAdapter)
 * @create by: chenwei
 * @date 2017/4/25 18:52
 */
public class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments = null;
    private List<String> mTitles = null;

    public BaseFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void add(int position, String title, Fragment fragment) {
        if (title != null && position < mTitles.size())
            mTitles.add(position, title);
        if (fragment != null && position < mFragments.size())
            mFragments.add(position, fragment);
    }

    public void remove(int position) {
        if (position < mTitles.size()) {
            mTitles.remove(position);
            mFragments.remove(position);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
