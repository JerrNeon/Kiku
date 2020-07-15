package com.jn.example.mvp.contract;

import com.jn.example.entiy.NewsVO;
import com.jn.example.request.RxObserver;
import com.jn.kiku.common.api.IRefreshView;
import com.jn.kiku.common.api.IRvView;
import com.jn.kiku.mvp.IBModel;
import com.jn.kiku.mvp.IBPresenter;
import com.jn.kiku.mvp.IBView;

import java.util.List;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：
 */
public interface NewsContract {

    interface IPresenter extends IBPresenter {

        void getNewList();
    }

    interface IView extends IBView, IRefreshView, IRvView<NewsVO> {

    }

    interface IModel extends IBModel {

        void getNewList(int pageIndex, int pageSize, RxObserver<List<NewsVO>> observer);
    }
}
