package com.jn.kiku.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Author：Stevie.Chen Time：2019/8/20
 * Class Comment：BaseFragmentPagerAdapter
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
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
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
