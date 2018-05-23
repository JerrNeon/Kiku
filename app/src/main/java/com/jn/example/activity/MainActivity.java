package com.jn.example.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jn.example.R;
import com.jn.example.entiy.XaResult2;
import com.jn.example.request.ApiService;
import com.jn.kiku.activity.RootMainActivity;
import com.jn.kiku.entiy.VersionUpdateVO;
import com.jn.kiku.retrofit.RetrofitManage;
import com.jn.kiku.utils.AppUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends RootMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendRequest();
    }

    @Override
    public int[] getMenuSelectedTextColorResources() {
        return new int[0];
    }

    @Override
    public int[] getMenuSelectedImgResources() {
        return new int[0];
    }

    @Override
    public int[] getMenuUnSelectedImgResources() {
        return new int[0];
    }

    @Override
    public String[] getMenuTitles() {
        return new String[0];
    }

    @Override
    public Fragment[] getMenuFragments() {
        return new Fragment[0];
    }

    @Override
    public void initMainView() {

    }

    @Override
    public void changeFragment(int position) {

    }

    @Override
    public void sendRequest() {
        RetrofitManage.getInstance()
                .create(ApiService.class)
                .getVersionUpdateInfo(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<XaResult2<VersionUpdateVO>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<XaResult2<VersionUpdateVO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(XaResult2<VersionUpdateVO> xaResult2) {
                        mVersionUpdateVO = xaResult2.getObject();
                        if (mVersionUpdateVO.getVersionNum() > AppUtils.getVersionCode(mContext)) {
                            mVersionUpdateVO
                                    .setAppIconResId(R.mipmap.ic_launcher)
                                    .setAppName(AppUtils.getAppName(mContext));
                            showVersionUpdateDialog();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    @Override
    protected void onResume() {
        registerVersionUpdateReceiver();
        super.onResume();
    }
}
