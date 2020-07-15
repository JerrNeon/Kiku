package com.jn.kiku.ttp.pay.wxpay;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author：Stevie.Chen Time：2019/8/2
 * Class Comment：微信支付信息实体(自己服务器返回)
 */
public class WxPayInfoVO implements Parcelable {

    private String appid;//	String	公众账号ID
    private String partnerid;//	String	商户号
    private String prepayid;//	String	预支付交易会话ID
    private String noncestr;//	String	随机字符串
    private String timestamp;//	String	时间戳
    private String sign	;//String	签名
    private String orderid;//	String	商户订单号

    public String getAppid() {
        return appid;
    }

    public WxPayInfoVO setAppid(String appid) {
        this.appid = appid;
        return this;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public WxPayInfoVO setPartnerid(String partnerid) {
        this.partnerid = partnerid;
        return this;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public WxPayInfoVO setPrepayid(String prepayid) {
        this.prepayid = prepayid;
        return this;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public WxPayInfoVO setNoncestr(String noncestr) {
        this.noncestr = noncestr;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public WxPayInfoVO setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public WxPayInfoVO setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public String getOrderid() {
        return orderid;
    }

    public WxPayInfoVO setOrderid(String orderid) {
        this.orderid = orderid;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appid);
        dest.writeString(this.partnerid);
        dest.writeString(this.prepayid);
        dest.writeString(this.noncestr);
        dest.writeString(this.timestamp);
        dest.writeString(this.sign);
        dest.writeString(this.orderid);
    }

    public WxPayInfoVO() {
    }

    protected WxPayInfoVO(Parcel in) {
        this.appid = in.readString();
        this.partnerid = in.readString();
        this.prepayid = in.readString();
        this.noncestr = in.readString();
        this.timestamp = in.readString();
        this.sign = in.readString();
        this.orderid = in.readString();
    }

    public static final Creator<WxPayInfoVO> CREATOR = new Creator<WxPayInfoVO>() {
        @Override
        public WxPayInfoVO createFromParcel(Parcel source) {
            return new WxPayInfoVO(source);
        }

        @Override
        public WxPayInfoVO[] newArray(int size) {
            return new WxPayInfoVO[size];
        }
    };
}
