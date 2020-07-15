package com.jn.example.mvp.model;

import com.jn.example.entiy.NewsVO;
import com.jn.example.mvp.contract.NewsContract;
import com.jn.example.request.ApiManager;
import com.jn.example.request.RxObserver;
import com.jn.kiku.mvp.BaseModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：
 */
public class NewsModel extends BaseModel implements NewsContract.IModel {

    @Override
    public void getNewList(int pageIndex, int pageSize, RxObserver<List<NewsVO>> observer) {
        ApiManager.getInstance().getApiService()
                .getNewList(pageIndex, pageSize, "video")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
