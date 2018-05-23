package com.jn.kiku.ttp.pay.wxpay;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (微信支付信息实体(自己服务器返回))
 * @create by: chenwei
 * @date 2017/3/10 11:21
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

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
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
