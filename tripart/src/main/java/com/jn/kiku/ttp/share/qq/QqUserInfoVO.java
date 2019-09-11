package com.jn.kiku.ttp.share.qq;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (QQ用户信息)
 * @create by: chenwei
 * @date 2017/9/8 11:29
 */
public class QqUserInfoVO implements Parcelable {


    /**
     * is_yellow_year_vip : 0
     * ret : 0
     * figureurl_qq_1 : http://q.qlogo.cn/qqapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/40
     * figureurl_qq_2 : http://q.qlogo.cn/qqapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/100
     * nickname : 小罗
     * yellow_vip_level : 0
     * msg :
     * figureurl_1 : http://qzapp.qlogo.cn/qzapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/50
     * vip : 0
     * level : 0
     * figureurl_2 : http://qzapp.qlogo.cn/qzapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/100
     * is_yellow_vip : 0
     * gender : 男
     * figureurl : http://qzapp.qlogo.cn/qzapp/222222/8C75BBE3DC6B0E9A64BD31449A3C8CB0/30
     */

    private String client_id;//应用的APPID
    private String openid;//QQ用户的唯一标示，对当前开发者账号唯一
    private String unionid;//QQ用户的统一标示，对当前开发者账号唯一
    private String access_token;//
    private int ret;
    private String figureurl_qq_1;//大小为40×40像素的QQ头像URL。
    private String figureurl_qq_2;//大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100×100的头像，但40×40像素则是一定会有。
    private String nickname;//用户在QQ空间的昵称。
    private String gender;//性别。 如果获取不到则默认返回”男”
    private String msg;//如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
    private String figureurl;//大小为30×30像素的QQ空间头像URL。
    private String figureurl_1;//大小为50×50像素的QQ空间头像URL。
    private String figureurl_2;//大小为100×100像素的QQ空间头像URL。
    private String vip;//标识用户是否为黄钻用户（0：不是；1：是）
    private String level;//黄钻等级
    private String yellow_vip_level;//黄钻等级
    private String is_yellow_vip;//标识用户是否为黄钻用户（0：不是；1：是）。
    private String is_yellow_year_vip;//标识是否为年费黄钻用户（0：不是； 1：是）

    public String getClient_id() {
        return client_id;
    }

    public QqUserInfoVO setClient_id(String client_id) {
        this.client_id = client_id;
        return this;
    }

    public String getOpenid() {
        return openid;
    }

    public QqUserInfoVO setOpenid(String openid) {
        this.openid = openid;
        return this;
    }

    public String getUnionid() {
        return unionid;
    }

    public QqUserInfoVO setUnionid(String unionid) {
        this.unionid = unionid;
        return this;
    }

    public String getAccess_token() {
        return access_token;
    }

    public QqUserInfoVO setAccess_token(String access_token) {
        this.access_token = access_token;
        return this;
    }

    public int getRet() {
        return ret;
    }

    public QqUserInfoVO setRet(int ret) {
        this.ret = ret;
        return this;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }

    public QqUserInfoVO setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
        return this;
    }

    public String getFigureurl_qq_2() {
        return figureurl_qq_2;
    }

    public QqUserInfoVO setFigureurl_qq_2(String figureurl_qq_2) {
        this.figureurl_qq_2 = figureurl_qq_2;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public QqUserInfoVO setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public QqUserInfoVO setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public QqUserInfoVO setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public QqUserInfoVO setFigureurl(String figureurl) {
        this.figureurl = figureurl;
        return this;
    }

    public String getFigureurl_1() {
        return figureurl_1;
    }

    public QqUserInfoVO setFigureurl_1(String figureurl_1) {
        this.figureurl_1 = figureurl_1;
        return this;
    }

    public String getFigureurl_2() {
        return figureurl_2;
    }

    public QqUserInfoVO setFigureurl_2(String figureurl_2) {
        this.figureurl_2 = figureurl_2;
        return this;
    }

    public String getVip() {
        return vip;
    }

    public QqUserInfoVO setVip(String vip) {
        this.vip = vip;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public QqUserInfoVO setLevel(String level) {
        this.level = level;
        return this;
    }

    public String getYellow_vip_level() {
        return yellow_vip_level;
    }

    public QqUserInfoVO setYellow_vip_level(String yellow_vip_level) {
        this.yellow_vip_level = yellow_vip_level;
        return this;
    }

    public String getIs_yellow_vip() {
        return is_yellow_vip;
    }

    public QqUserInfoVO setIs_yellow_vip(String is_yellow_vip) {
        this.is_yellow_vip = is_yellow_vip;
        return this;
    }

    public String getIs_yellow_year_vip() {
        return is_yellow_year_vip;
    }

    public QqUserInfoVO setIs_yellow_year_vip(String is_yellow_year_vip) {
        this.is_yellow_year_vip = is_yellow_year_vip;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.client_id);
        dest.writeString(this.openid);
        dest.writeString(this.unionid);
        dest.writeString(this.access_token);
        dest.writeInt(this.ret);
        dest.writeString(this.figureurl_qq_1);
        dest.writeString(this.figureurl_qq_2);
        dest.writeString(this.nickname);
        dest.writeString(this.gender);
        dest.writeString(this.msg);
        dest.writeString(this.figureurl);
        dest.writeString(this.figureurl_1);
        dest.writeString(this.figureurl_2);
        dest.writeString(this.vip);
        dest.writeString(this.level);
        dest.writeString(this.yellow_vip_level);
        dest.writeString(this.is_yellow_vip);
        dest.writeString(this.is_yellow_year_vip);
    }

    public QqUserInfoVO() {
    }

    protected QqUserInfoVO(Parcel in) {
        this.client_id = in.readString();
        this.openid = in.readString();
        this.unionid = in.readString();
        this.access_token = in.readString();
        this.ret = in.readInt();
        this.figureurl_qq_1 = in.readString();
        this.figureurl_qq_2 = in.readString();
        this.nickname = in.readString();
        this.gender = in.readString();
        this.msg = in.readString();
        this.figureurl = in.readString();
        this.figureurl_1 = in.readString();
        this.figureurl_2 = in.readString();
        this.vip = in.readString();
        this.level = in.readString();
        this.yellow_vip_level = in.readString();
        this.is_yellow_vip = in.readString();
        this.is_yellow_year_vip = in.readString();
    }

    public static final Creator<QqUserInfoVO> CREATOR = new Creator<QqUserInfoVO>() {
        @Override
        public QqUserInfoVO createFromParcel(Parcel source) {
            return new QqUserInfoVO(source);
        }

        @Override
        public QqUserInfoVO[] newArray(int size) {
            return new QqUserInfoVO[size];
        }
    };
}
