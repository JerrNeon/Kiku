package com.jn.kiku.utils.form;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jn.kiku.utils.QMUtil;

import java.util.regex.Pattern;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (表单验证)
 * @create by: chenwei
 * @date 2016/10/11 11:51
 */
public class FormUtils {

    /**
     * 验证所有表单是否为空
     *
     * @param mContext
     * @param errors    错误信息
     * @param editTexts 要验证的EditText
     * @return 有一个为空返回true，全都不为空返回false
     */
    public static boolean validate(Context mContext, String[] errors, EditText... editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            EditText et = editTexts[i];
            if (QMUtil.isEmpty(et.getText().toString().trim())) {
                Toast.makeText(mContext, errors[i] + "不能为空", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    /**
     * 验证所有表单是否为空
     *
     * @param mContext
     * @param errors    错误信息
     * @param textViews 要验证的TextView
     * @return 有一个为空返回true，全都不为空返回false
     */
    public static boolean validate(Context mContext, String[] errors, TextView... textViews) {
        for (int i = 0; i < textViews.length; i++) {
            TextView tv = textViews[i];
            if (QMUtil.isEmpty(tv.getText().toString().trim())) {
                Toast.makeText(mContext, errors[i] + "不能为空", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    /**
     * 正则表达式验证所有表单(提示信息为 "错误信息"+ "格式不正确")
     *
     * @param mContext
     * @param regexs    正则表达式
     * @param errors    错误信息
     * @param editTexts 要验证的EditText
     * @return 有一个验证失败返回true，全都都验证成功返回false
     */
    public static boolean validate(Context mContext, String[] regexs, String[] errors, EditText... editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            EditText et = editTexts[i];
            if (!Pattern.matches(regexs[i], et.getText().toString().trim())) {
                Toast.makeText(mContext, errors[i] + "格式不正确", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    /**
     * 正则表达式验证所有表单(提示信息为 "错误信息")
     *
     * @param mContext
     * @param regexs    正则表达式
     * @param errors    错误信息
     * @param editTexts 要验证的EditText
     * @return 有一个验证失败返回true，全都都验证成功返回false
     */
    public static boolean validateAll(Context mContext, String[] regexs, String[] errors, EditText... editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            EditText et = editTexts[i];
            if (!Pattern.matches(regexs[i], et.getText().toString().trim())) {
                Toast.makeText(mContext, errors[i], Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }
}
