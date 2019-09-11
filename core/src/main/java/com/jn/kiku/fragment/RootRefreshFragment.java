package com.jn.kiku.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jn.kiku.R;
import com.jn.kiku.annonation.LoadMoreEnableType;
import com.jn.kiku.annonation.RefreshOperateType;
import com.jn.kiku.annonation.RefreshViewType;
import com.jn.kiku.common.api.IRefreshView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * Author：Stevie.Chen Time：2019/8/14
 * Class Comment：带刷新的Fragment
 */
public abstract class RootRefreshFragment extends RootFragment implements IRefreshView, OnRefreshLoadMoreListener {

    protected int mPageIndex, mPageSize, mTotalSize, mTotalPage;//分页信息
    protected @RefreshOperateType
    int mRefreshOperateType;//操作类型
    protected SmartRefreshLayout mSmartRefreshLayout;//根布局
    protected ClassicsHeader mClassicsHeader;//刷新布局
    protected ClassicsFooter mClassicsFooter;//加载更多布局
    protected FrameLayout mFlRootContainer;//显示内容根布局

    @Override
    protected int getLayoutResourceId() {
        return R.layout.common_refresh_layout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setRefreshOperateType(RefreshOperateType.ON_CREATE);
        initRefreshView();
        return mView;
    }

    @Override
    public void showProgressDialog() {
        if (mRefreshOperateType == RefreshOperateType.ON_CREATE || mRefreshOperateType == RefreshOperateType.ON_RELOAD)
            super.showProgressDialog();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        setRefreshOperateType(RefreshOperateType.ON_REFRESH);
        mSmartRefreshLayout.resetNoMoreData();//恢复没有更多数据的原始状态
        mPageIndex = getInitPageIndex();
        sendRequest();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        setRefreshOperateType(RefreshOperateType.ON_ONLOADMORE);
        if (getLoadMoreEnableType() == LoadMoreEnableType.EMPTY) {
            mPageIndex++;
            sendRequest();
        } else {
            if (isLoadMoreEnable()) {
                mPageIndex++;
                sendRequest();
            } else {
                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据
            }
        }
    }

    @Override
    public int getPageIndex() {
        return mPageIndex;
    }

    @Override
    public int getPageSize() {
        return mPageSize;
    }

    @Override
    public int getTotalSize() {
        return mTotalSize;
    }

    @Override
    public int getTotalPage() {
        return mTotalPage;
    }

    @Override
    public void initRefreshView() {
        mPageIndex = getInitPageIndex();
        mPageSize = getInitPageSize();

        mSmartRefreshLayout = mView.findViewById(R.id.srl_root);
        mClassicsHeader = mView.findViewById(R.id.srlH_root);
        mClassicsFooter = mView.findViewById(R.id.srlF_root);
        mFlRootContainer = mView.findViewById(R.id.fl_rootContainer);
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
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);//刷新和加载更多监听
        mSmartRefreshLayout.setEnableAutoLoadMore(true);//是否启用列表惯性滑动到底部时自动加载更多(默认启用)
        mSmartRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容(默认是)
        mSmartRefreshLayout.setEnableFooterFollowWhenNoMoreData(true);//是否在全部加载结束之后Footer跟随内容1.0.4
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
    public int getLoadMoreEnableType() {
        return LoadMoreEnableType.EMPTY;
    }

    @Override
    public boolean isLoadMoreEnable() {
        return true;
    }
}
