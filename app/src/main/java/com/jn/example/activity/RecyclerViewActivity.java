package com.jn.example.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.jn.example.adapter.NewsAdapter;
import com.jn.example.entiy.NewsListVO;
import com.jn.example.entiy.NewsVO;
import com.jn.example.request.ApiService;
import com.jn.example.request.BaseResponseObserver;
import com.jn.kiku.activity.RootRvActivity;
import com.jn.kiku.adapter.BaseRvAdapter;
import com.jn.kiku.annonation.PermissionType;
import com.jn.kiku.retrofit.RetrofitManage;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;

public class RecyclerViewActivity extends RootRvActivity<NewsVO> {

    @Override
    public BaseRvAdapter<NewsVO> getAdapter() {
        return new NewsAdapter(mActivity);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    public Observable getRequestObservable() {
        return RetrofitManage.getInstance()
                .create(ApiService.class)
                .getVideoList(mPageIndex, mPageSize);
    }

    @Override
    public Observer getResponseObserver() {
        return new BaseResponseObserver<NewsListVO>(this) {
            @Override
            public void onSuccess(NewsListVO newsListVO) {
                showLoadSuccessView(newsListVO.getPage().getTotal(), newsListVO.getList());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showLoadErrorView();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("首页");
        requestPermission(PermissionType.LOCATION, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                logI("请求结果： " + aBoolean);
            }
        });
    }

}
