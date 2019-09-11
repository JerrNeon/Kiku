package com.jn.kiku.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jn.kiku.R;
import com.jn.kiku.adapter.BaseRvAdapter;
import com.jn.kiku.annonation.LoadCompleteType;
import com.jn.kiku.annonation.LoadMoreEnableType;
import com.jn.kiku.annonation.RefreshOperateType;
import com.jn.kiku.annonation.RefreshViewType;
import com.jn.kiku.common.api.IRvView;
import com.jn.kiku.utils.NetUtils;
import com.jn.kiku.utils.ViewsUtils;
import com.jn.kiku.utils.recyclerview.GlideOnScrollListener;
import com.jn.kiku.widget.recyclerview.api.RvSimpleClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：RootRvActivity
 */
public abstract class RootRvActivity<T> extends RootRefreshActivity implements IRvView<T> {

    protected RecyclerView mRecyclerView;//RecyclerView
    protected View mEmptyView;//empty or failure view
    protected ImageView mIvLoadingFailure;//empty or failure icon
    protected TextView mTvLoadingFailure;//empty or failure hint text
    protected BaseRvAdapter<T> mAdapter = null;//adapter

    @Override
    public int getLayoutItemResourceId() {
        return R.layout.common_rv;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRvView();
        sendRequest();
    }

    @Override
    public void sendRequest() {
        super.sendRequest();
        showProgressDialog();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mAdapter.setNewData(null);//clear data
        super.onRefresh(refreshLayout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
    }

    @Override
    public boolean isLoadMoreEnable() {
        if (getLoadMoreEnableType() == LoadMoreEnableType.TOTAL)
            return mAdapter.getItemCount() < getTotalSize();
        else if (getLoadMoreEnableType() == LoadMoreEnableType.PAGE)
            return getPageSize() < getTotalPage();
        return super.isLoadMoreEnable();
    }

    @Override
    public void initRvView() {
        mRecyclerView = findViewById(R.id.rv_common);
        mAdapter = getAdapter();
        mRecyclerView.setLayoutManager(getLayoutManager() != null ? getLayoutManager() : new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new GlideOnScrollListener(mActivity));//Glide加载优化
        mRecyclerView.addOnItemTouchListener(new RvSimpleClickListener<>(this, mAdapter));

        mAdapter.bindToRecyclerView(mRecyclerView);
        mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.common_loadingfailure, mRecyclerView, false);
        mIvLoadingFailure = mEmptyView.findViewById(R.id.iv_commonLoadingFailure);
        mTvLoadingFailure = mEmptyView.findViewById(R.id.tv_commonLoadingFailure);
        mEmptyView.setOnClickListener(this::onClickLoadFailure);
        mAdapter.setEmptyView(mEmptyView);//set empty or failure view
        mAdapter.isUseEmpty(false);//don t show empty or failure view first
    }

    @Override
    public void showLoadCompleteView(int loadCompleteType, List<T> data) {
        dismissProgressDialog();
        mSmartRefreshLayout.finishRefresh();//refresh complete
        mSmartRefreshLayout.finishLoadMore();//load more complete
        int refreshViewType = getRefreshViewType();
        if (loadCompleteType == LoadCompleteType.SUCCESS) {//load success
            if (!isEmpty(data)) {//有数据
                mSmartRefreshLayout.setEnableRefresh(refreshViewType == RefreshViewType.ONLY_REFRESH || refreshViewType == RefreshViewType.ALL);
                mSmartRefreshLayout.setEnableLoadMore(refreshViewType == RefreshViewType.ONLY_LOADMORE || refreshViewType == RefreshViewType.ALL);
                mAdapter.isUseEmpty(false);//don t show empty or failure view
                if (mPageIndex == getInitPageIndex())
                    mAdapter.setNewData(data);
                else
                    mAdapter.addData(data);
            } else if (mPageIndex == getInitPageIndex()) {//first page no data
                setLoadFailureResource(R.drawable.ic_kiku_nodata, R.string.kiku_load_nodata);
                mSmartRefreshLayout.setEnableRefresh(false);//invalid refresh
                mSmartRefreshLayout.setEnableLoadMore(false);//invalid load more
                mAdapter.isUseEmpty(true);//show empty or failure view
                mAdapter.setNewData(null);
            } else if (mPageIndex > getInitPageIndex()) {//next page no data
                if (getLoadMoreEnableType() == LoadMoreEnableType.EMPTY) {
                    mAdapter.isUseEmpty(false);//don t show empty or failure view
                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();//all data load complete
                }
            }
            mEmptyView.setEnabled(false);//enable false
        } else if (loadCompleteType == LoadCompleteType.ERROR) {//no net or load failure
            if (mPageIndex == getInitPageIndex()) {//first page
                mSmartRefreshLayout.setEnableRefresh(false);//invalid refresh
                mSmartRefreshLayout.setEnableLoadMore(false);//invalid load more
                mAdapter.isUseEmpty(true);//show empty or failure view
                mAdapter.setNewData(null);
                if (!NetUtils.isConnected(mContext)) {//no net
                    setLoadFailureResource(R.drawable.ic_kiku_nonet, R.string.kiku_load_nonet);
                } else {//other reason(connect failure or server error)
                    setLoadFailureResource(R.drawable.ic_kiku_nonet, R.string.kiku_load_failure);
                }
                mEmptyView.setEnabled(true);//enable true
            } else {
                if (refreshViewType == RefreshViewType.ONLY_LOADMORE || refreshViewType == RefreshViewType.ALL) {
                    mPageIndex--;//reset pageIndex when load more failure
                    mSmartRefreshLayout.finishLoadMore(false);//load more failure
                }
            }
        }
    }

    @Override
    public void showLoadSuccessView(List<T> data) {
        showLoadCompleteView(LoadCompleteType.SUCCESS, data);
    }

    @Override
    public void showLoadSuccessView(int total, List<T> data) {
        int loadMoreEnableType = getLoadMoreEnableType();
        if (loadMoreEnableType == LoadMoreEnableType.TOTAL)
            mTotalSize = total;
        else if (loadMoreEnableType == LoadMoreEnableType.PAGE)
            mTotalPage = total;
        showLoadSuccessView(data);
    }

    @Override
    public void showLoadErrorView() {
        showLoadCompleteView(LoadCompleteType.ERROR, null);
    }

    @Override
    public void setLoadFailureResource(int loadFailureDrawableRes, int loadFailureStringRes) {
        if (loadFailureDrawableRes != 0)
            mIvLoadingFailure.setImageResource(loadFailureDrawableRes);
        if (loadFailureStringRes != 0)
            mTvLoadingFailure.setText(loadFailureStringRes);
    }

    @Override
    public void onClickLoadFailure(View view) {
        setRefreshOperateType(RefreshOperateType.ON_RELOAD);
        mPageIndex = getInitPageIndex();
        sendRequest();
    }

    @Override
    public void cancelItemAnimator() {
        ViewsUtils.cancelItemAnimator(mRecyclerView);
    }

    @Override
    public void onItemClick(BaseRvAdapter adapter, View view, int position, T item) {

    }

    @Override
    public void onItemLongClick(BaseRvAdapter adapter, View view, int position, T item) {

    }

    @Override
    public void onItemChildClick(BaseRvAdapter adapter, View view, int position, T item) {

    }

    @Override
    public void onItemChildLongClick(BaseRvAdapter adapter, View view, int position, T item) {

    }
}
