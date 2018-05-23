package com.jn.example.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.jn.example.adapter.NewsAdapter;
import com.jn.example.entiy.NewsListVO;
import com.jn.example.entiy.NewsVO;
import com.jn.example.entiy.XaResult;
import com.jn.example.request.ApiService;
import com.jn.example.request.BaseResponseObserver;
import com.jn.kiku.activity.RootRvActivity;
import com.jn.kiku.adapter.BaseRvAdapter;
import com.jn.kiku.annonation.LoadCompleteType;
import com.jn.kiku.annonation.PermissionType;
import com.jn.kiku.retrofit.RetrofitManage;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

    @Override
    public void sendRequest() {
        super.sendRequest();
        RetrofitManage.getInstance()
                .create(ApiService.class)
                .getVideoList(mPageIndex, mPageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<XaResult<NewsListVO>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseResponseObserver<NewsListVO>(this) {
                    @Override
                    public void onSuccess(NewsListVO newsListVO) {
                        mTotalSize = newsListVO.getPage().getTotal();
                        showLoadCompleteView(LoadCompleteType.SUCCESS, newsListVO.getList());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showLoadCompleteView(LoadCompleteType.ERROR, null);
                    }
                });
    }
}
