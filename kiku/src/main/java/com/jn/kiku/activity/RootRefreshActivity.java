package com.jn.kiku.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.jn.kiku.R;
import com.jn.kiku.annonation.RefreshOperateType;
import com.jn.kiku.annonation.RefreshViewType;
import com.jn.kiku.common.Config;
import com.jn.kiku.common.api.IRefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RootRefreshActivity)
 * @create by: chenwei
 * @date 2018/5/17 14:42
 */
public abstract class RootRefreshActivity extends RootTbActivity implements IRefreshView, OnRefreshLoadMoreListener {

    protected int mPageIndex, mPageSize, mTotalSize;//page info
    protected @RefreshOperateType
    int mRefreshOperateType;//operate type
    protected SmartRefreshLayout mSmartRefreshLayout;//root layout
    protected ClassicsHeader mClassicsHeader;//refresh layout
    protected ClassicsFooter mClassicsFooter;//load more layout
    protected FrameLayout mFlRootContainer;//show content layout

    @Override
    public int getLayoutResourceId() {
        return R.layout.common_refresh_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRefreshOperateType(RefreshOperateType.ON_CREATE);
        initRefreshView();
    }

    @Override
    public void showProgressDialog() {
        if (mRefreshOperateType == RefreshOperateType.ON_CREATE || mRefreshOperateType == RefreshOperateType.ON_RELOAD)
            super.showProgressDialog();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        setRefreshOperateType(RefreshOperateType.ON_REFRESH);
        mSmartRefreshLayout.setNoMoreData(false);//reset no more data origin status
        mPageIndex = getPageIndex();
        sendRequest();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        setRefreshOperateType(RefreshOperateType.ON_ONLOADMORE);
        if (isLoadMoreEnable()) {
            mPageIndex++;
            sendRequest();
        } else {
            mSmartRefreshLayout.finishLoadMoreWithNoMoreData();//load complete and no more data
        }
    }

    @Override
    public int getPageIndex() {
        return Config.PAGE_INDEX;
    }

    @Override
    public int getPageSize() {
        return Config.PAGE_SIZE;
    }

    @Override
    public int getTotalSize() {
        return mTotalSize;
    }

    @Override
    public void initRefreshView() {
        mPageIndex = getPageIndex();
        mPageSize = getPageSize();

        mSmartRefreshLayout = findViewById(R.id.srl_root);
        mClassicsHeader = findViewById(R.id.srlH_root);
        mClassicsFooter = findViewById(R.id.srlF_root);
        mFlRootContainer = findViewById(R.id.fl_rootContainer);
        if (getLayoutItemResourceId() != 0) {
            mFlRootContainer.addView(LayoutInflater.from(mContext).inflate(getLayoutItemResourceId(), null, false),
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }

        int recyclerViewType = getRefreshViewType();
        if (recyclerViewType == RefreshViewType.ALL) {
            mSmartRefreshLayout.setEnableRefresh(true);
            mSmartRefreshLayout.setEnableLoadMore(true);
        } else if (recyclerViewType == RefreshViewType.NONE) {
            mSmartRefreshLayout.setEnableRefresh(false);
            mSmartRefreshLayout.setEnableLoadMore(false);
        } else if (recyclerViewType == RefreshViewType.ONLY_REFRESH) {
            mSmartRefreshLayout.setEnableRefresh(true);
            mSmartRefreshLayout.setEnableLoadMore(false);
        } else {
            mSmartRefreshLayout.setEnableRefresh(false);
            mSmartRefreshLayout.setEnableLoadMore(true);
        }
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);//refresh and more listener
        mSmartRefreshLayout.setEnableAutoLoadMore(true);//SmartRefreshLayout Api
        mSmartRefreshLayout.setEnableScrollContentWhenLoaded(true);//SmartRefreshLayout Api
        mSmartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true);//SmartRefreshLayout Api
    }

    @Override
    public int getRefreshViewType() {
        return RefreshViewType.ALL;
    }

    @Override
    public void setRefreshOperateType(int operateType) {
        mRefreshOperateType = operateType;
    }

    @Override
    public boolean isLoadMoreEnable() {
        return true;
    }
}
