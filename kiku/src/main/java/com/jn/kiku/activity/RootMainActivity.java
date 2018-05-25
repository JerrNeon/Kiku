package com.jn.kiku.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SimpleArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jn.kiku.R;
import com.jn.kiku.common.api.IMainView;
import com.jn.kiku.dialog.VersionUpdateDialog;
import com.jn.kiku.entiy.VersionUpdateVO;
import com.jn.kiku.receiver.IReceiverListener;
import com.jn.kiku.receiver.VersionUpdateReceiver;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RootMainActivity)
 * @create by: chenwei
 * @date 2018/5/17 15:37
 */
public abstract class RootMainActivity extends RootActivity implements IMainView, TabLayout.OnTabSelectedListener {

    private long mTimeExit = 0;//Back press time
    private final long mTimeInterval = 2000;//two times Interval

    protected int mFlRootMainContainerId = R.id.fl_RootMainContainer;//main content resource ID
    protected TabLayout mTabLayout = null;

    protected int[] mMenuSelectedTextColorResources = null;//selected text color resource id
    protected int[] mMenuSelectedImgResources = null;//selected text icon resource id
    protected int[] mMenuUnSelectedImgResources = null;//unselected text color resource id
    protected String[] mMenuTitles = null;//text title resource
    protected Fragment[] mMenuFragments = null;//fragment color resource id
    protected SimpleArrayMap<Integer, Fragment> mMenuFragmentMap = new SimpleArrayMap<>();//save Fragment add to FragmentTransaction
    protected VersionUpdateReceiver mVersionUpdateReceiver = null;//versionUpdate downLoad receiver
    protected VersionUpdateDialog mVersionUpdateDialog = null;//versionUpdate dialog
    protected VersionUpdateVO mVersionUpdateVO = null;//versionUpdate content

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_main_layout);
        mMenuSelectedTextColorResources = getMenuSelectedTextColorResources();
        mMenuSelectedImgResources = getMenuSelectedImgResources();
        mMenuUnSelectedImgResources = getMenuUnSelectedImgResources();
        mMenuTitles = getMenuTitles();
        mMenuFragments = getMenuFragments();
        initMainView();
        changeFragment(0);
    }

    @Override
    public void initMainView() {
        mTabLayout = findViewById(R.id.tl_RootMain);
        if (mMenuSelectedImgResources != null) {
            for (int i = 0; i < mMenuSelectedImgResources.length; i++) {
                View menuView = LayoutInflater.from(mContext).inflate(R.layout.common_mainmenu_layout, null, false);
                ImageView iv = menuView.findViewById(R.id.iv_menu);
                TextView tv = menuView.findViewById(R.id.tv_menu);
                tv.setTextColor(ContextCompat.getColor(mContext, mMenuSelectedTextColorResources[i]));
                if (i == 0) {
                    iv.setImageResource(mMenuSelectedImgResources[i]);
                    tv.setSelected(true);
                } else {
                    iv.setImageResource(mMenuUnSelectedImgResources[i]);
                    tv.setSelected(false);
                }
                tv.setText(mMenuTitles[i]);
                mTabLayout.addTab(mTabLayout.newTab().setCustomView(menuView));
            }
        }
        mTabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void changeFragment(int position) {
        if (position >= mMenuFragments.length)
            throw new ArrayIndexOutOfBoundsException("position is more than total: " + mMenuFragments.length);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = mMenuFragmentMap.get(position);
        if (fragment == null) {
            fragment = mMenuFragments[position];
            mMenuFragmentMap.put(position, fragment);
            ft.add(mFlRootMainContainerId, fragment);
        }
        hideAllFragment(ft);
        ft.show(fragment);
        ft.commit();
    }

    @Override
    public void hideAllFragment(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : mMenuFragments) {
            if (fragment != null)
                fragmentTransaction.hide(fragment);
        }
    }

    @Override
    public boolean isExit() {
        if ((System.currentTimeMillis() - mTimeExit) > mTimeInterval) {
            if (mTabLayout.getSelectedTabPosition() == 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.app_exitNoticeMessage),
                        Toast.LENGTH_SHORT).show();
                mTimeExit = System.currentTimeMillis();
            } else {
                mTabLayout.getTabAt(0).select();
            }
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void registerVersionUpdateReceiver() {
        if (mVersionUpdateReceiver == null) {
            mVersionUpdateReceiver = new VersionUpdateReceiver(new IReceiverListener() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    dismissProgressDialog();
                    String message = intent.getStringExtra(VersionUpdateReceiver.VERSION_UPDATE_ACTION);
                    showToast(message);
                    showVersionUpdateDialog();
                }
            });
        }
        IntentFilter intentFilter = new IntentFilter(VersionUpdateReceiver.VERSION_UPDATE_ACTION);
        registerReceiver(mVersionUpdateReceiver, intentFilter);
    }

    @Override
    public void unregisterVersionUpdateReceiver() {
        if (mVersionUpdateReceiver != null)
            unregisterReceiver(mVersionUpdateReceiver);
    }

    @Override
    public void showVersionUpdateDialog() {
        if (mVersionUpdateVO == null)
            throw new NullPointerException("mVersionUpdateVO is null,please set VersionUpdateVO info");
        if (mVersionUpdateDialog == null) {
            mVersionUpdateDialog = VersionUpdateDialog.newInstance(VersionUpdateDialog.class, mVersionUpdateVO);
        }
        mVersionUpdateDialog.show(getSupportFragmentManager(), "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        View menuView = tab.getCustomView();
        ImageView iv = menuView.findViewById(R.id.iv_menu);
        TextView tv = menuView.findViewById(R.id.tv_menu);
        iv.setImageResource(mMenuSelectedImgResources[tab.getPosition()]);
        tv.setSelected(true);
        changeFragment(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View menuView = tab.getCustomView();
        ImageView iv = menuView.findViewById(R.id.iv_menu);
        TextView tv = menuView.findViewById(R.id.tv_menu);
        iv.setImageResource(mMenuUnSelectedImgResources[tab.getPosition()]);
        tv.setSelected(false);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onPause() {
        unregisterVersionUpdateReceiver();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (isExit())
            super.onBackPressed();
    }
}
