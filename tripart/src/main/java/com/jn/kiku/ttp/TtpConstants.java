package com.jn.kiku.ttp;

/**
 * Author：Stevie.Chen Time：2019/8/5
 * Class Comment：第三方平台信息
 */
public class TtpConstants {

    public static final String QQ_APP_ID = "101581810";//QQ_APP_ID

    public static final String WECHAT_APP_ID = "wxcb3e45232fbf891a";//WeChat_APP_ID
    public static final String WECHAT_SECRET = "18d8f26a240889cff485e94164cb6c40";//WeChat_SECRET

    public static final String SINA_APP_KEY = "3186197994";//新浪AppKey
    public static String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";//新浪回调地址(默认地址)
    public static final String SINA_SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";//应用申请的高级权限

    public static final String ALIPAY_APPID = "";//支付宝支付业务：入参app_id
    public static final String ALIPAY_PID = "";//支付宝账户登录授权业务：入参pid值
    public static final String ALIPAY_TARGET_ID = "";//支付宝账户登录授权业务：入参target_id值
    /* 商户私钥，pkcs8格式 */
    /* 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /* 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /* RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /* 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String ALIPAY_RSA2_PRIVATE = "";
    public static final String ALIPAY_RSA_PRIVATE = "";

    /**
     * 诸葛io AppKey
     */
    public static final String ZHUGE_APPKEY = "90fc349d64e24ca8ac1bd203c490f5f8";

    /**
     * 环信
     */
    public static final String MOB_APPKEY = "";
    public static final String MOB_TENANTID = "";

    /**
     * Bugtags
     */
    private static final String BUGTAGS_APPKEY_TEST = "fef4541a51ad5eee99987a6f732aff66";
    private static final String BUGTAGS_APPSECRET_TEST = "26d6266afabed9f2583971231bfc585f";
    private static final String BUGTAGS_APPKEY_ONLINE = "59dfcaa9e058711e4c3b6f4419b1bf04";
    private static final String BUGTAGS_APPSECRET_ONLINE = "2054c07406a86ba82735e79b4e8d2945";
    public static String BUGTAGS_APPKEY = BuildConfig.DEBUG ? BUGTAGS_APPKEY_TEST : BUGTAGS_APPKEY_ONLINE;
    public static String BUGTAGS_APPSECRET = BuildConfig.DEBUG ? BUGTAGS_APPSECRET_TEST : BUGTAGS_APPSECRET_ONLINE;
}
