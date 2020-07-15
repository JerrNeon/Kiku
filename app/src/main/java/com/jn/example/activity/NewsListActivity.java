package com.jn.example.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.jn.example.adapter.NewsAdapter;
import com.jn.example.entiy.NewsVO;
import com.jn.example.mvp.contract.NewsContract;
import com.jn.example.mvp.presenter.NewsPresenter;
import com.jn.kiku.activity.RootRvActivity;
import com.jn.kiku.adapter.BaseRvAdapter;
import com.jn.kiku.annonation.PermissionType;

import cn.jzvd.JzvdStd;

public class NewsListActivity extends RootRvActivity<NewsContract.IPresenter, NewsVO> implements NewsContract.IView {

    @Override
    public NewsContract.IPresenter createPresenter() {
        return new NewsPresenter();
    }

    @Override
    public BaseRvAdapter<NewsVO> getAdapter() {
        return new NewsAdapter(mActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("新闻列表");
        requestPermission(PermissionType.LOCATION, aBoolean -> logI("请求结果： " + aBoolean));
    }

    @Override
    public void sendRequest() {
        super.sendRequest();
        mPresenter.getNewList();
    }

    @Override
    public void onItemClick(BaseRvAdapter adapter, View view, int position, NewsVO item) {
        super.onItemClick(adapter, view, position, item);
        String videoUrl = item.getVideo();
        if (!TextUtils.isEmpty(videoUrl)) {
            JzvdStd.startFullscreenDirectly(this, JzvdStd.class, videoUrl, "");
        }
    }

    @Override
    public void onBackPressed() {
        if (JzvdStd.backPress())
            return;
        super.onBackPressed();
    }
}
