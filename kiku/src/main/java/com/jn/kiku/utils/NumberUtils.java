package com.jn.kiku.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (数字操作类)
 * @create by: chenwei
 * @date 2016/10/10 18:34
 */
public class NumberUtils {

    /**
     * 数据格式化.
     *
     * @param pattern the pattern
     * @param value   the i
     * @return the string
     */
    public static String codeFormat(String pattern, Object value) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    /**
     * 格式化金额.
     *
     * @param value the value
     * @return the string
     */
    public static String formatCurrency2String(Long value) {
        if (value == null || "0".equals(String.valueOf(value))) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value / 100.00);
    }

    /**
     * 格式化金额.
     *
     * @param priceFormat the price format
     * @return the long
     */
    public static Long formatCurrency2Long(String priceFormat) {
        BigDecimal bg = new BigDecimal(priceFormat);
        Long price = bg.multiply(new BigDecimal(100)).longValue();
        return price;
    }

    /**
     * 格式化金额
     *
     * @param StrBd
     * @return
     */
    public static BigDecimal formatString2Bigdecimal(String StrBd) {
        BigDecimal bd = new BigDecimal(StrBd);
        //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    /**
     * 格式化金额.(保留两位小数)
     *
     * @param value the value
     * @return the string
     */
    public static String formatDouble2String(double value) {
        if (QMUtil.isEmpty(value)) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    /**
     * 格式化折扣.(保留一位小数)
     *
     * @param value the value
     * @return the string
     */
    public static String formatDiscount(double value) {
        try {
            if (QMUtil.isEmpty(value)) {
                return "0.0";
            }
            BigDecimal bigDec = new BigDecimal(value);
            double total = bigDec.setScale(1, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
            DecimalFormat df = new DecimalFormat("0.0");
            return df.format(total);
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0";
        }
    }

    /**
     * 生成固定长度的随机字符和数字
     *
     * @param len
     * @return
     */
    public static String generateRandomCharAndNumber(Integer len) {
        StringBuffer sb = new StringBuffer();
        for (Integer i = 0; i < len; i++) {
            int intRand = (int) (Math.random() * 52);
            int numValue = (int) (Math.random() * 10);
            char base = (intRand < 26) ? 'A' : 'a';
            char c = (char) (base + intRand % 26);
            if (numValue % 2 == 0) {
                sb.append(c);
            } else {
                sb.append(numValue);
            }
        }
        return sb.toString();
    }

    /**
     * @param count 位数，如果是1就产生1位的数字，如果是2就产生2位数字，依次类推
     * @return
     * @Title: getRandomNumber
     * @Description: 获取随机数
     */
    public static String getRandomNumber(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            int rand = (int) (Math.random() * 10);
            result += rand;
        }
        return result;
    }

    /**
     * 逗号分隔价格并带两位小数
     *
     * @param price
     * @return
     */
    public static String formatPriceWith2Decimal(double price) {
        if (price == 0)
            return "0.00";
        else if (price < 1) {
            String result = new DecimalFormat("0.00").format(price);
            return result;
        } else {
            String result = new DecimalFormat(",###,###.00").format(price);
            return result;
        }
    }

    /**
     * 逗号分隔价格
     *
     * @param price
     * @return
     */
    public static String formatPriceWithComma(double price) {
        if (price == 0)
            return removeZeroBehindPoint(String.valueOf(price));
        else if (price < 1) {
            String result = new DecimalFormat("0.00").format(price);
            return removeZeroBehindPoint(result);
        } else {
            String result = new DecimalFormat(",###,###.00").format(price);
            return removeZeroBehindPoint(result);
        }
    }

    /**
     * 逗号分隔价格
     *
     * @param price
     * @return
     */
    public static String formatPriceWithComma(long price) {
        return new DecimalFormat(",###,###").format(price);
    }

    /**
     * 去掉后面无用的零,小数点后面全是零则去掉小数点
     *
     * @param object
     * @return
     */
    public static String removeZeroBehindPoint(Object object) {
        String str = String.valueOf(object);
        if (str.indexOf(".") > 0) {
            //正则表达
            str = str.replaceAll("0+?$", "");//去掉后面无用的零
            str = str.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return str;
    }

    /**
     * 距离转换 m转化为Km
     *
     * @param distance
     * @return
     */
    public static String translateDistance(int distance) {
        try {
            if (distance < 1000)
                return distance + "m";
            else {
                double result = distance / 1000.0;
                BigDecimal bigDecimal = new BigDecimal(result);
                result = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                DecimalFormat df = new DecimalFormat("0.00");
                return removeZeroBehindPoint(df.format(result)) + "km";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 距离转换 m转化为Km
     *
     * @param distance
     * @return
     */
    public static String translateDistance(double distance) {
        try {
            if (distance < 1000) {
                //取整四舍五入
                BigDecimal bigDecimal = new BigDecimal(distance);
                double result = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                DecimalFormat df = new DecimalFormat("0");
                return df.format(result) + "m";
            } else {
                //保留一位小数四舍五入
                double result = distance / 1000.0;
                BigDecimal bigDecimal = new BigDecimal(result);
                result = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                DecimalFormat df = new DecimalFormat("0.0");
                return removeZeroBehindPoint(df.format(result)) + "km";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 手机号中间四位用*号代替
     *
     * @param phone
     * @return
     */
    public static String formatPhone(String phone) {
        try {
            if (phone == null)
                return "";
            else if ("".equals(phone))
                return phone;
            else
                return phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
        } catch (Exception e) {
            e.printStackTrace();
            return phone;
        }
    }

    /**
     * 名称只显示第一位，其余都用*号代替
     *
     * @param name
     * @return
     */
    public static String formatName(String name) {
        try {
            if (name == null)
                return "";
            else if ("".equals(name))
                return name;
            else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < name.length() - 1; i++) {
                    sb.append("*");
                }
                return name.substring(0, 1) + sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return name;
        }
    }

    /**
     * 科学计数法转成数字
     *
     * @param str
     * @return
     */
    public static String scientific2Str(String str) {
        try {
            BigDecimal bd = new BigDecimal(str);
            return bd.toPlainString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
