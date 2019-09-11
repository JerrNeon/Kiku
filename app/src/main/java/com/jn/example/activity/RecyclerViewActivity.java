package com.jn.example.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.jn.example.adapter.NewsAdapter;
import com.jn.example.entiy.NewsVO;
import com.jn.kiku.activity.RootRvActivity;
import com.jn.kiku.adapter.BaseRvAdapter;
import com.jn.kiku.annonation.PermissionType;

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
        requestPermission(PermissionType.LOCATION, aBoolean -> logI("请求结果： " + aBoolean));
    }

    @Override
    public void sendRequest() {

    }
}
