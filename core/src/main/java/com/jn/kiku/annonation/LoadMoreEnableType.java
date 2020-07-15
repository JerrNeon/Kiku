package com.jn.kiku.annonation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.jn.kiku.annonation.LoadMoreEnableType.EMPTY;
import static com.jn.kiku.annonation.LoadMoreEnableType.PAGE;
import static com.jn.kiku.annonation.LoadMoreEnableType.TOTAL;

/**
 * Author：Stevie.Chen Time：2019/4/20
 * Class Comment：是否可以加载更多
 */
@IntDef({TOTAL, EMPTY, PAGE})
@Retention(RetentionPolicy.SOURCE)
public @interface LoadMoreEnableType {
    int TOTAL = 1;//根据总数来判断
    int EMPTY = 2;//根据下一页数据是否为空来判断
    int PAGE = 3;//根据总页数来判断
}
