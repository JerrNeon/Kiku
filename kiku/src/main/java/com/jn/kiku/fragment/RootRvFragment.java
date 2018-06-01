package com.jn.kiku.fragment;

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
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @param <T> 适配器实体泛型
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (基础列表界面)
 * @create by: chenwei
 * @date 2018/5/16 15:49
 */
public abstract class RootRvFragment<T> extends RootRefreshFragment implements IRvView<T>,
        BaseRvAdapter.OnItemClickListener, BaseRvAdapter.OnItemLongClickListener, BaseRvAdapter.OnItemChildClickListener,
        BaseRvAdapter.OnItemChildLongClickListener {

    protected RecyclerView mRecyclerView;//列表
    protected View mEmptyView;//空数据或加载失败View
    protected ImageView mIvLoadingFailure;//加载失败或空数据图标
    protected TextView mTvLoadingFailure;//加载失败或空数据提示文字
    protected BaseRvAdapter<T> mAdapter = null;//适配器
    protected Observable mRequestObservable = null;//Request Observable
    protected Observer mResponseObserver = null;//Response Observer

    @Override
    public int getLayoutItemResourceId() {
        return R.layout.common_rv;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRvView();
        //非懒加载Fragment在onActivityCreated(方法中发起请求)
        if (!isLazyLoadFragment())
            sendRequest();
    }

    @Override
    public void sendRequest() {
        super.sendRequest();
        showProgressDialog();
        subscribe();
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

    @SuppressWarnings("unchecked")
    @Override
    public void subscribe() {
        if (mRequestObservable != null && mResponseObserver != null) {
            mRequestObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                    .subscribe(mResponseObserver);
        }
    }

    @Override
    public void initRvView() {
        mRecyclerView = mView.findViewById(R.id.rv_common);
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
        mAdapter.setEmptyView(mEmptyView);//设置加载失败或空数据View
        mAdapter.isUseEmpty(false);//初次不显示加载失败布局或空数据布局

        mRequestObservable = getRequestObservable();
        mResponseObserver = getResponseObserver();
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
                if (mPageIndex == getPageIndex())
                    mAdapter.setNewData(data);
                else
                    mAdapter.addData(data);
            } else if (mPageIndex == getPageIndex()) {//第一页无数据
                setLoadFailureResource(R.drawable.ic_kiku_nodata, R.string.kiku_load_nodata);
                mSmartRefreshLayout.setEnableRefresh(false);//禁用刷新
                mSmartRefreshLayout.setEnableLoadMore(false);//禁用加载
                mAdapter.isUseEmpty(true);//显示加载失败布局或空布局
                mAdapter.setNewData(null);
            }
            mEmptyView.setEnabled(false);//不可点击
        } else if (loadCompleteType == LoadCompleteType.ERROR) {//无网络、加载失败
            if (mPageIndex == getPageIndex()) {//第一页
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
                    mSmartRefreshLayout.finishLoadMore(false);//加载更多失败
                }
            }
        }
    }

    @Override
    public void showLoadSuccessView(int totalSize, List<T> data) {
        mTotalSize = totalSize;
        showLoadCompleteView(LoadCompleteType.SUCCESS, data);
    }

    @Override
    public void showLoadErrorView() {
        showLoadCompleteView(LoadCompleteType.ERROR, null);
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
