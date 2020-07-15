package com.jn.kiku.annonation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.jn.kiku.annonation.LoadCompleteType.ERROR;
import static com.jn.kiku.annonation.LoadCompleteType.SUCCESS;

/**
 * Author：Stevie.Chen Time：2019/8/20
 * Class Comment：加载完成类型
 */
@IntDef({ERROR, SUCCESS})
@Retention(RetentionPolicy.SOURCE)
public @interface LoadCompleteType {
    int SUCCESS = 1;//加载成功
    int ERROR = 2;//加载失败
}
