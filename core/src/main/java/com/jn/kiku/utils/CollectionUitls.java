package com.jn.kiku.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (集合工具类)
 * @create by: chenwei
 * @date 2017/11/21 10:56
 */
public class CollectionUitls {

    /**
     * 排序
     *
     * @param list       数据
     * @param comparator 排序规则
     * @param <T>
     */
    public static <T> void sort(List<T> list, Comparator<? super T> comparator) {
        Collections.sort(list, comparator);
    }

}
