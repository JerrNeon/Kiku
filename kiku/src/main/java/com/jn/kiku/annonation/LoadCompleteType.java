package com.jn.kiku.annonation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.jn.kiku.annonation.LoadCompleteType.ERROR;
import static com.jn.kiku.annonation.LoadCompleteType.SUCCESS;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (加载完成类型)
 * @create by: chenwei
 * @date 2018/5/15 18:14
 */
@IntDef({ERROR, SUCCESS})
@Retention(RetentionPolicy.SOURCE)
public @interface LoadCompleteType {
    int SUCCESS = 1;//加载成功
    int ERROR = 2;//加载失败
}
