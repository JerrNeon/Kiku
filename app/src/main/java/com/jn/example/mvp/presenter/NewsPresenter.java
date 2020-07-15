package com.jn.example.mvp.presenter;

import com.jn.example.entiy.NewsVO;
import com.jn.example.mvp.contract.NewsContract;
import com.jn.example.mvp.model.NewsModel;
import com.jn.example.request.RxObserver;
import com.jn.kiku.mvp.BasePresenter;

import java.util.List;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：
 */
public class NewsPresenter extends BasePresenter<NewsContract.IView, NewsContract.IModel> implements NewsContract.IPresenter {

    @Override
    protected NewsContract.IModel getModel() {
        return new NewsModel();
    }

    @Override
    public void getNewList() {
        mModel.getNewList(mView.getPageIndex(), mView.getPageSize(), new RxObserver<List<NewsVO>>(this) {
            @Override
            public void onSuccess(List<NewsVO> newsVOS) {
                mView.showLoadSuccessView(newsVOS);
            }
        });
    }
}
