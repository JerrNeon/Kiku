package com.jn.kiku.utils;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (反射工具类)
 * @create by: chenwei
 * @date 2016/10/12 9:40
 */
public class ReflectUtils {

    /**
     * 获取泛型类型
     *
     * @param subclass
     * @return
     */
    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized
                .getActualTypeArguments()[0]);
    }
}
