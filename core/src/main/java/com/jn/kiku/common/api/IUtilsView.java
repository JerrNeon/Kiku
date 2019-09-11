package com.jn.kiku.common.api;

import com.jn.common.util.QMUtil;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：工具
 */
public interface IUtilsView {

    /**
     * 判断对象是否为空
     */
    default boolean isEmpty(Object obj) {
        return QMUtil.isEmpty(obj);
    }

    /**
     * 检查字符串是否为空，为空则返回""
     */
    default String checkStr(String str) {
        return QMUtil.checkStr(str);
    }

    /**
     * String转成基础类型对象
     *
     * @param defaultNumber 默认值
     */
    default <T extends Number> T strToObject(String str, Number defaultNumber) {
        return QMUtil.strToObject(str, defaultNumber);
    }
}
