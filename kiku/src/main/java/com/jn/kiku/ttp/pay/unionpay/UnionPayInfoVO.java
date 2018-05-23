package com.jn.kiku.ttp.pay.unionpay;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (银联支付结果实体)
 * @create by: chenwei
 * @date 2017/3/10 11:19
 */
public class UnionPayInfoVO implements Parcelable {

    private String tn;//	String	交易流水号
    private String orderId;//	String	商户订单号
    private String txnAmt;//	String	交易金额(单位:分)
    private String notify_url;//	String	服务器异步通知页面路径

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tn);
        dest.writeString(this.orderId);
        dest.writeString(this.txnAmt);
        dest.writeString(this.notify_url);
    }

    public UnionPayInfoVO() {
    }

    protected UnionPayInfoVO(Parcel in) {
        this.tn = in.readString();
        this.orderId = in.readString();
        this.txnAmt = in.readString();
        this.notify_url = in.readString();
    }

    public static final Creator<UnionPayInfoVO> CREATOR = new Creator<UnionPayInfoVO>() {
        @Override
        public UnionPayInfoVO createFromParcel(Parcel source) {
            return new UnionPayInfoVO(source);
        }

        @Override
        public UnionPayInfoVO[] newArray(int size) {
            return new UnionPayInfoVO[size];
        }
    };
}
