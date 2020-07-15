package com.jn.kiku.ttp.pay.wxpay;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2017/3/10 12:01
 */
public interface WxPayURL {

    /**
     * 生成预支付订交易单
     * <p>
     * 参数--->appid:应用ID
     * 参数--->mch_id:商户号
     * 参数--->nonce_str:随机字符串
     * 参数--->sign:签名
     * 参数--->body:商品描述
     * 参数--->out_trade_no:商户订单号
     * 参数--->total_fee:总金额
     * 参数--->spbill_create_ip:终端IP
     * 参数--->notify_url:通知地址
     * 参数--->trade_type:交易类型
     * <p>
     * 返回--->return_code:状态码
     * 返回--->return_msg:信息
     * <p>
     * return_code为SUCCESS时
     * 返回--->appid:应用APPID
     * 返回--->mch_id:商户号
     * 返回--->device_info:设备号
     * 返回--->nonce_str:随机字符串
     * 返回--->sign:签名
     * 返回--->result_code:业务结果
     * 返回--->err_code:错误代码
     * 返回--->err_code_des:错误代码描述
     * <p>
     * return_code 和result_code都为SUCCESS时
     * 返回--->trade_type:交易类型
     * 返回--->prepay_id:预支付交易会话标识
     */
    String GENERATE_PREPAID_ORDERS = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
