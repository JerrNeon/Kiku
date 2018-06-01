package com.jn.kiku.common.api;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jn.kiku.adapter.BaseRvAdapter;
import com.jn.kiku.annonation.LoadCompleteType;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * @param <T> 适配器实体泛型
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RecyclerView)
 * @create by: chenwei
 * @date 2018/5/15 16:05
 */
public interface IRvView<T> {

    /**
     * 获取适配器
     *
     * @return
     */
    BaseRvAdapter<T> getAdapter();

    /**
     * 返回RecyclerView的LayoutManager
     * 为空时默认为LinearLayoutManager
     *
     * @return LinearLayoutManager/GridLayoutManager/StaggeredGridLayoutManager
     */
    RecyclerView.LayoutManager getLayoutManager();

    /**
     * 获取请求Observable
     *
     * @return
     */
    Observable getRequestObservable();

    /**
     * 获取响应Observer
     *
     * @return
     */
    Observer getResponseObserver();

    /**
     * 关联被观察者和观察者，发送请求
     */
    void subscribe();

    /**
     * 初始化刷新相关控件
     */
    void initRvView();

    /**
     * 显示完成布局
     *
     * @param loadCompleteType 加载完成类型
     * @param data             数据
     */
    void showLoadCompleteView(@LoadCompleteType int loadCompleteType, List<T> data);

    /**
     * 显示成功布局
     *
     * @param totalSize 总数
     * @param data      数据
     */
    void showLoadSuccessView(int totalSize, List<T> data);

    /**
     * 显示失败布局
     */
    void showLoadErrorView();

    /**
     * 设置加载失败或数据为空布局资源
     *
     * @param loadFailureDrawableRes 加载失败或数据为空显示图标资源
     * @param loadFailureStringRes   加载失败或数据为空显示提示文字资源
     */
    void setLoadFailureResource(@DrawableRes int loadFailureDrawableRes, @StringRes int loadFailureStringRes);

    /**
     * 点击加载失败或数据为空布局
     *
     * @param view
     */
    void onClickLoadFailure(View view);

    /**
     * 子项点击事件
     *
     * @param adapter
     * @param view
     * @param item
     */
    void onItemClick(BaseQuickAdapter adapter, View view, T item);

    /**
     * 子项长按时间
     *
     * @param adapter
     * @param view
     * @param item
     * @return
     */
    boolean onItemLongClick(BaseQuickAdapter adapter, View view, T item);

    /**
     * 子项控件点击事件
     *
     * @param adapter
     * @param view
     * @param item
     */
    void onItemChildClick(BaseQuickAdapter adapter, View view, T item);

    /**
     * 子项控件长按时间
     *
     * @param adapter
     * @param view
     * @param item
     * @return
     */
    boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, T item);
}
