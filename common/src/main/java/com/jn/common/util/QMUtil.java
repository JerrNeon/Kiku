package com.jn.common.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Author：Stevie.Chen Time：2019/8/12
 * Class Comment：数据操作类
 */
public class QMUtil {

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if (pObj.equals(""))
            return true;
        if (pObj instanceof String) {
            return ((String) pObj).length() == 0;
        } else if (pObj instanceof Collection) {
            return ((Collection) pObj).size() == 0;
        } else if (pObj instanceof Map) {
            return ((Map) pObj).size() == 0;
        }
        return false;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素>0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null)
            return false;
        if (pObj.equals(""))
            return false;
        if (pObj instanceof String) {
            return ((String) pObj).length() != 0;
        } else if (pObj instanceof Collection) {
            return ((Collection) pObj).size() != 0;
        } else if (pObj instanceof Map) {
            return ((Map) pObj).size() != 0;
        }
        return true;
    }

    /**
     * 判断两个字符串是否相同(顺序一定是一样的)
     */
    public static boolean isIncludeAllChar(String str1, String str2) {
        boolean flag = true;

        if (str1.length() != str2.length()) {
            flag = false;
        } else {
            if (!str1.equals(str2))
                flag = false;
        }
        return flag;
    }

    /**
     * 判断两个字符串是否相同(不管顺序是否一样)
     */
    public static boolean isIncludeSameChar(String str1, String str2) {
        boolean flag = true;

        if (str1.length() != str2.length()) {
            flag = false;
        } else {
            char[] str1Arr = str1.toCharArray();
            Arrays.sort(str1Arr);
            char[] str2Arr = str2.toCharArray();
            Arrays.sort(str2Arr);
            for (int i = 0; i < str2Arr.length; i++) {
                if (str2Arr[i] == str1Arr[i]) {
                    continue;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 检查字符串(如果为空，返回"",否则返回原信息)
     *
     * @param str 原字符串
     * @return 返回
     */
    public static String checkStr(String str) {
        if (isEmpty(str))
            return "";
        else
            return str;
    }

    /**
     * 字符串转为整型对象
     *
     * @param str           要转换的字符串
     * @param defaultNumber 默认值
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T strToObject(String str, Number defaultNumber) {
        Number result = null;
        try {
            if (defaultNumber instanceof Integer)
                result = Integer.parseInt(str);
            else if (defaultNumber instanceof Long)
                result = Long.parseLong(str);
            else if (defaultNumber instanceof Short)
                result = Short.parseShort(str);
            else if (defaultNumber instanceof Float)
                result = Float.parseFloat(str);
            else if (defaultNumber instanceof Double)
                result = Double.parseDouble(str);
        } catch (NumberFormatException | NullPointerException e) {
            result = defaultNumber;
        }
        return (T) result;
    }

    /**
     * 复制文本到剪切板
     */
    public static void copyToClipboard(Context context, String text) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}