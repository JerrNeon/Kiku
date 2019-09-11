package com.jn.kiku.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
 * Class Comment：基础列表界面
 */
public abstract class RootRvFragment<T> extends RootRefreshFragment implements IRvView<T> {

    protected RecyclerView mRecyclerView;//列表
    protected View mEmptyView;//空数据或加载失败View
    protected ImageView mIvLoadingFailure;//加载失败或空数据图标
    protected TextView mTvLoadingFailure;//加载失败或空数据提示文字
    protected BaseRvAdapter<T> mAdapter = null;//适配器

    @Override
    public int getLayoutItemResourceId() {
        return R.layout.common_rv;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initRvView();
        //非懒加载Fragment在onActivityCreated(方法中发起请求)
        if (!isLazyLoadFragment())
            sendRequest();
        return mView;
    }

    @Override
    public void sendRequest() {
        super.sendRequest();
        showProgressDialog();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mAdapter.setNewData(null);//清除数据
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
        mRecyclerView = mView.findViewById(R.id.rv_common);
        mAdapter = getAdapter();
        mRecyclerView.setLayoutManager(getLayoutManager() != null ? getLayoutManager() : new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new GlideOnScrollListener(mFragment));//Glide加载优化
        mRecyclerView.addOnItemTouchListener(new RvSimpleClickListener<>(this, mAdapter));

        mAdapter.bindToRecyclerView(mRecyclerView);
        mEmptyView = LayoutInflater.from(mContext).inflate(R.layout.common_loadingfailure, mRecyclerView, false);
        mIvLoadingFailure = mEmptyView.findViewById(R.id.iv_commonLoadingFailure);
        mTvLoadingFailure = mEmptyView.findViewById(R.id.tv_commonLoadingFailure);
        mEmptyView.setOnClickListener(this::onClickLoadFailure);
        mAdapter.setEmptyView(mEmptyView);//设置加载失败或空数据View
        mAdapter.isUseEmpty(false);//初次不显示加载失败布局或空数据布局
    }

    @Override
    public void showLoadCompleteView(int loadCompleteType, List<T> data) {
        dismissProgressDialog();
        mSmartRefreshLayout.finishRefresh();//刷新完成
        mSmartRefreshLayout.finishLoadMore();//加载更多完成
        int refreshViewType = getRefreshViewType();
        if (loadCompleteType == LoadCompleteType.SUCCESS) {//加载成功
            if (!isEmpty(data)) {//有数据
                mSmartRefreshLayout.setEnableRefresh(refreshViewType == RefreshViewType.ONLY_REFRESH || refreshViewType == RefreshViewType.ALL);
                mSmartRefreshLayout.setEnableLoadMore(refreshViewType == RefreshViewType.ONLY_LOADMORE || refreshViewType == RefreshViewType.ALL);
                mAdapter.isUseEmpty(false);//不显示加载失败布局或空布局
                if (mPageIndex == getInitPageIndex())
                    mAdapter.setNewData(data);
                else
                    mAdapter.addData(data);
            } else if (mPageIndex == getInitPageIndex()) {//第一页无数据
                setLoadFailureResource(R.drawable.ic_kiku_nodata, R.string.kiku_load_nodata);
                mSmartRefreshLayout.setEnableRefresh(false);//禁用刷新
                mSmartRefreshLayout.setEnableLoadMore(false);//禁用加载
                mAdapter.isUseEmpty(true);//显示加载失败布局或空布局
                mAdapter.setNewData(null);
            } else if (mPageIndex > getInitPageIndex()) {//next page no data
                if (getLoadMoreEnableType() == LoadMoreEnableType.EMPTY) {
                    mAdapter.isUseEmpty(false);//don t show empty or failure view
                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData();//all data load complete
                }
            }
            mEmptyView.setEnabled(false);//不可点击
        } else if (loadCompleteType == LoadCompleteType.ERROR) {//无网络、加载失败
            if (mPageIndex == getInitPageIndex()) {//第一页
                mSmartRefreshLayout.setEnableRefresh(false);//禁用刷新
                mSmartRefreshLayout.setEnableLoadMore(false);//禁用加载
                mAdapter.isUseEmpty(true);//显示加载失败布局或空布局
                mAdapter.setNewData(null);
                if (!NetUtils.isConnected(mContext)) {//无网络
                    setLoadFailureResource(R.drawable.ic_kiku_nonet, R.string.kiku_load_nonet);
                } else {//其他(连接失败或服务器错误等)
                    setLoadFailureResource(R.drawable.ic_kiku_nonet, R.string.kiku_load_failure);
                }
                mEmptyView.setEnabled(true);//可点击
            } else {
                if (refreshViewType == RefreshViewType.ONLY_LOADMORE || refreshViewType == RefreshViewType.ALL) {
                    mPageIndex--;//reset pageIndex when load more failure
                    mSmartRefreshLayout.finishLoadMore(false);//加载更多失败
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
