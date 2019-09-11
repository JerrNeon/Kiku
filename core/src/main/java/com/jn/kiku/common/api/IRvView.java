package com.jn.kiku.common.api;

import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.jn.kiku.adapter.BaseRvAdapter;
import com.jn.kiku.annonation.LoadCompleteType;
import com.jn.kiku.annonation.LoadMoreEnableType;

import java.util.List;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：RecyclerView
 */
public interface IRvView<T> {

    /**
     * 获取适配器
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
     * @param data 数据
     */
    void showLoadSuccessView(List<T> data);

    /**
     * 显示成功布局
     *
     * @param total 总数 / 总页数 根据{@link LoadMoreEnableType}来判断
     * @param data  数据
     */
    void showLoadSuccessView(int total, List<T> data);

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
     */
    void onClickLoadFailure(View view);

    /**
     * 关闭RecyclerView更新动画
     */
    void cancelItemAnimator();

    /**
     * 子项点击事件
     */
    void onItemClick(BaseRvAdapter adapter, View view, int position, T item);

    /**
     * 子项长按时间
     */
    void onItemLongClick(BaseRvAdapter adapter, View view, int position, T item);

    /**
     * 子项控件点击事件
     */
    void onItemChildClick(BaseRvAdapter adapter, View view, int position, T item);

    /**
     * 子项控件长按时间
     */
    void onItemChildLongClick(BaseRvAdapter adapter, View view, int position, T item);
}
