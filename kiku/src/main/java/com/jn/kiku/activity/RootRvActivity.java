package com.jn.kiku.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jn.kiku.R;
import com.jn.kiku.adapter.BaseRvAdapter;
import com.jn.kiku.annonation.LoadCompleteType;
import com.jn.kiku.annonation.RefreshOperateType;
import com.jn.kiku.annonation.RefreshViewType;
import com.jn.kiku.common.api.IRvView;
import com.jn.kiku.utils.NetUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

/**
 * @param <T>
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RootRvActivity)
 * @create by: chenwei
 * @date 2018/5/15 15:53
 */
public abstract class RootRvActivity<T> extends RootRefreshActivity implements IRvView<T>,
        BaseRvAdapter.OnItemClickListener, BaseRvAdapter.OnItemLongClickListener, BaseRvAdapter.OnItemChildClickListener,
        BaseRvAdapter.OnItemChildLongClickListener {

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
        return mAdapter.getItemCount() < getTotalSize();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        onItemClick(adapter, view, mAdapter.getItem(position));
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        return onItemLongClick(adapter, view, mAdapter.getItem(position));
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        onItemChildClick(adapter, view, mAdapter.getItem(position));
    }

    @Override
    public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
        return onItemChildLongClick(adapter, view, mAdapter.getItem(position));
    }

    @Override
    public void initRvView() {
        mRecyclerView = findViewById(R.id.rv_common);
        mAdapter = getAdapter();
        mRecyclerView.setLayoutManager(getLayoutManager() != null ? getLayoutManager() : new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.bindToRecyclerView(mRecyclerView);
        mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.common_loadingfailure, null, false);
        mIvLoadingFailure = mEmptyView.findViewById(R.id.iv_commonLoadingFailure);
        mTvLoadingFailure = mEmptyView.findViewById(R.id.tv_commonLoadingFailure);
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLoadFailure(view);
            }
        });
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
                if (mPageIndex == getPageIndex())
                    mAdapter.setNewData(data);
                else
                    mAdapter.addData(data);
            } else if (mPageIndex == getPageIndex()) {//first page no data
                setLoadFailureResource(R.drawable.ic_kiku_nodata, R.string.kiku_load_nodata);
                mSmartRefreshLayout.setEnableRefresh(false);//invalid refresh
                mSmartRefreshLayout.setEnableLoadMore(false);//invalid load more
                mAdapter.isUseEmpty(true);//show empty or failure view
                mAdapter.setNewData(null);
            }
            mEmptyView.setEnabled(false);//enable false
        } else if (loadCompleteType == LoadCompleteType.ERROR) {//no net or load failure
            if (mPageIndex == getPageIndex()) {//first page
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
    public void setLoadFailureResource(int loadFailureDrawableRes, int loadFailureStringRes) {
        mIvLoadingFailure.setImageResource(loadFailureDrawableRes);
        mTvLoadingFailure.setText(loadFailureStringRes);
    }

    @Override
    public void onClickLoadFailure(View view) {
        setRefreshOperateType(RefreshOperateType.ON_RELOAD);
        mPageIndex = getPageIndex();
        sendRequest();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, T item) {

    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, T item) {
        return false;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, T item) {

    }

    @Override
    public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, T item) {
        return false;
    }

}
