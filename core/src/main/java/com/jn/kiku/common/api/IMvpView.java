package com.jn.kiku.common.api;

import com.jn.kiku.mvp.IBPresenter;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：MVP
 */
public interface IMvpView<P extends IBPresenter> {

    void initPresenter();

    default P createPresenter() {
        return null;
    }
}
