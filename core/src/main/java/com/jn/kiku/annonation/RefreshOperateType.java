package com.jn.kiku.annonation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.jn.kiku.annonation.RefreshOperateType.ON_CREATE;
import static com.jn.kiku.annonation.RefreshOperateType.ON_ONLOADMORE;
import static com.jn.kiku.annonation.RefreshOperateType.ON_REFRESH;
import static com.jn.kiku.annonation.RefreshOperateType.ON_RELOAD;

/**
 * Author：Stevie.Chen Time：2019/8/20
 * Class Comment：RecyclerView界面操作类型
 */
@IntDef({ON_CREATE, ON_REFRESH, ON_ONLOADMORE, ON_RELOAD})
@Retention(RetentionPolicy.SOURCE)
public @interface RefreshOperateType {
    int ON_CREATE = 1;//RecyclerView所在界面执行力OnCreate方法
    int ON_REFRESH = 2;//下拉刷新
    int ON_ONLOADMORE = 3;//上拉加载更多
    int ON_RELOAD = 4;//加载失败或重新加载
}
