package com.jn.kiku.ttp.pay.unionpay;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (银联支付常量)
 * @create by: chenwei
 * @date 2017/3/10 14:44
 */
public interface UnionPayConstants {

    /**
     * 插件可用
     */
    int PLUGIN_VALID = 0;
    /**
     * 插件未导入
     */
    int PLUGIN_NOT_INSTALLED = -1;
    /**
     * 插件更新
     */
    int PLUGIN_NEED_UPGRADE = 2;
    
    /**
     * 流水号获取地址
     */
    String GET_TN_URL = "http://202.101.25.178:8080/sim/gettn";
    /**
     * 银联正式环境
     */
    String UNION_OFFICIAL_CONNECT = "00";
    /**
     * 银联测试环境
     */
    String UNION_TEST_CONNECT = "01";
}
