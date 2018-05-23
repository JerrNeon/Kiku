package com.jn.kiku.utils.form;

import java.util.regex.Pattern;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (正则表达式验证)
 * @create by: chenwei
 * @date 2017/11/28 11:38
 */
public class RegexUtils {

    /**
     * 匹配正则
     *
     * @param regex 正则表达式
     * @param str   字符串
     * @return
     */
    public static boolean check(String regex, String str) {
        return Pattern.matches(regex, str);
    }

    /**
     * 验证手机号
     *
     * @param str 字符串
     * @return
     */
    public static boolean checkMobile(String str) {
        return Pattern.matches(RegexConstans.regexMobile, str);
    }
}
