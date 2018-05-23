package com.jn.kiku.common.api;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (工具)
 * @create by: chenwei
 * @date 2018/5/11 14:24
 */
public interface IUtilsView {

    /**
     * 判断对象是否为空
     *
     * @param obj
     * @return
     */
    boolean isEmpty(Object obj);

    /**
     * 检查字符串是否为空，为空则返回""
     *
     * @param str
     * @return
     */
    String checkStr(String str);

    /**
     * 基础类型对象转成String
     *
     * @param object
     * @param <T>
     * @return
     */
    <T extends Number> String objToStr(T object);

    /**
     * String转成基础类型对象
     *
     * @param str
     * @param <T>
     * @param defaultNumber 默认值
     * @return
     */
    <T extends Number> T strToObject(String str, Number defaultNumber);
}
