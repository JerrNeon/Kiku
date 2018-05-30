package com.jn.kiku.ttp;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (第三方平台常量)
 * @create by: chenwei
 * @date 2016/10/9 14:28
 */
public class TtpConstants {

    public static String QQ_APP_ID = "1104773229";//QQ_APP_ID

    public static String WECHAT_APP_ID = "wx5721522a59dcea59";//WeChat_APP_ID
    public static String WECHAT_SECRET = "357e9f7061664ea53f0b74aa0f80ce44";//WeChat_SECRET

    public static String SINA_APP_KEY = "2255724049";//新浪AppKey
    public static final String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";//新浪回调地址(默认地址)
    public static final String SINA_SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";//应用申请的高级权限

    public static String ALIPAY_APPID = "2088189563528610";//支付宝支付业务：入参app_id
    public static String ALIPAY_PID = "2088121453528610";//支付宝账户登录授权业务：入参pid值
    public static String ALIPAY_TARGET_ID = "20881214514228610";//支付宝账户登录授权业务：入参target_id值
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String ALIPAY_RSA2_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKeanODDjeHxIUYbmBOlC1M6QH5wjfU0BhjO71eB5/x7XoA//JNUfsIQbfOFZkZRG8T7MNFSKNSe4DSXVnjYm5WwtJwv4aVsBXUPmFIgHt6W++kjaZypVd6PAHzk5FR1xBlUpM58KMrI8jMqUpmebYFNo33dmaXMOW8MAbQv8Zv5AgMBAAECgYBNeexg/iTdOBEQjnrrQdUNYRUlHbSRj73tw+LhybxKe2EA2hNQq7N41A1vj8/qW14B3bgAWwFi4Bp2VSr06/RnK2mCtXB3OX1H64c8hJ8w9FFiGj9X4q1m1lyZ+jTWOvbMsmf5Rn2vaOBd8fbwbJuQptF9ZzOTq/yh2IhvjLCn8QJBAM8vIhWPGb3Pd2Ak/XdeJniA8cOKvRNwAG6IgoD8lvRoj74xK69UoV5AISPRIxub8ozv7HtD2kSgt28VEfmLDqUCQQDPGBiWZVVWX/W36Gq1AhOyzfYEYnFGpAThslL5yeg/kFINDbhlHoqoSpCfxMhfT46Um11TJnU205u+IaQsAUvFAkAFrlAr8SmOh9LJIxqEHGPHqBl4+CPpFYgdf8a8TLDC8N8IIwcEnrhyAiYmekSRLDyBWs7MLnccrJ96/0Pn6MU1AkB4fgmYP79GMTDzXvvu8xVo/GK+rFRCCJ56ftm+UhaaHStQQwJde0aroi7BdqoqokxP9JF5FrAuRTKhjktJ+zsNAkEAlUkfT0Qa5yY5VJncQcryQXhz0kkum0IF7WVnZksCnpmD3k/xYNZZXf7eezERnA4NBr/y3IP9kX3TmNzJV8fx2w==";
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

    public static class Builder {
        private String qqAppId;
        private String wechatAppId;
        private String wechatSecret;
        private String sinaAppId;
        private String alipayAppId;
        private String alipayPId;
        private String alipayTargetId;
        private String bugtagsAppKey;
        private String bugtagsAppSecret;

        public Builder() {

        }

        /**
         * QQ分享和登录
         *
         * @param qqAppId
         * @return
         */
        public Builder setQQInfo(String qqAppId) {
            this.qqAppId = qqAppId;
            return this;
        }

        /**
         * 微信分享
         *
         * @param wechatAppId
         * @return
         */
        public Builder setWechatInfo(String wechatAppId) {
            this.wechatAppId = wechatAppId;
            return this;
        }

        /**
         * 微信分享和登录
         *
         * @param wechatAppId
         * @param wechatSecret
         * @return
         */
        public Builder setWechatInfo(String wechatAppId, String wechatSecret) {
            this.wechatAppId = wechatAppId;
            this.wechatSecret = wechatSecret;
            return this;
        }

        /**
         * 新浪微博分享和登录
         *
         * @param sinaAppId
         * @return
         */
        public Builder setSinaInfo(String sinaAppId) {
            this.sinaAppId = sinaAppId;
            return this;
        }

        /**
         * 支付宝支付
         *
         * @param alipayAppId
         * @return
         */
        public Builder setAlipayInfo(String alipayAppId) {
            this.alipayAppId = alipayAppId;
            return this;
        }

        /**
         * 支付宝登录
         *
         * @param alipayAppId
         * @param alipayPId
         * @param alipayTargetId
         * @return
         */
        public Builder setAlipayInfo(String alipayAppId, String alipayPId, String alipayTargetId) {
            this.alipayAppId = alipayAppId;
            this.alipayPId = alipayPId;
            this.alipayTargetId = alipayTargetId;
            return this;
        }

        /**
         * BugTags错误信息收集
         *
         * @param bugtagsAppKey
         * @return
         */
        public Builder setBugtagsInfo(String bugtagsAppKey) {
            this.bugtagsAppKey = bugtagsAppKey;
            return this;
        }

        /**
         * BugTags错误信息收集,bugtagsAppSecret好像暂无用到
         *
         * @param bugtagsAppKey
         * @param bugtagsAppSecret
         * @return
         */
        public Builder setBugtagsInfo(String bugtagsAppKey, String bugtagsAppSecret) {
            this.bugtagsAppKey = bugtagsAppKey;
            this.bugtagsAppSecret = bugtagsAppSecret;
            return this;
        }

        public void builder() {
            if (qqAppId != null)
                QQ_APP_ID = qqAppId;
            if (wechatAppId != null)
                WECHAT_APP_ID = wechatAppId;
            if (wechatSecret != null)
                WECHAT_SECRET = wechatSecret;
            if (sinaAppId != null)
                SINA_APP_KEY = sinaAppId;
            if (alipayAppId != null)
                ALIPAY_APPID = alipayAppId;
            if (alipayAppId != null)
                ALIPAY_PID = alipayPId;
            if (alipayAppId != null)
                ALIPAY_TARGET_ID = alipayTargetId;
            if (alipayAppId != null)
                BUGTAGS_APPKEY = bugtagsAppKey;
            if (alipayAppId != null)
                BUGTAGS_APPSECRET = bugtagsAppSecret;
        }
    }
}
