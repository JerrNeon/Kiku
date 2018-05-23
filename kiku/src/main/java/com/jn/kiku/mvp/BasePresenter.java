package com.jn.kiku.mvp;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (BasePresenter)
 * @create by: chenwei
 * @date 2017/11/17 15:38
 */
public class BasePresenter<T extends BaseModel, V> {

    protected T mModel;
    protected V mView;

    public BasePresenter(V view) {
        mView = view;
    }
}