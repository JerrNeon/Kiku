package com.jn.kiku.ttp.share.sina;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (新浪个人信息)
 * @create by: chenwei
 * @date 2017/9/7 17:47
 */
public class SinaUserInfoVO implements Parcelable {


    /**
     * id : 1404376560
     * screen_name : zaku
     * name : zaku
     * province : 11
     * city : 5
     * location : 北京 朝阳区
     * description : 人生五十年，乃如梦如幻；有生斯有死，壮士复何憾。
     * url : http://blog.sina.com.cn/zaku
     * profile_image_url : http://tp1.sinaimg.cn/1404376560/50/0/1
     * domain : zaku
     * gender : m
     * followers_count : 1204
     * friends_count : 447
     * statuses_count : 2908
     * favourites_count : 0
     * created_at : Fri Aug 28 00:00:00 +0800 2009
     * following : false
     * allow_all_act_msg : false
     * geo_enabled : true
     * verified : false
     * allow_all_comment : true
     * avatar_large : http://tp1.sinaimg.cn/1404376560/180/0/1
     * verified_reason :
     * follow_me : false
     * online_status : 0
     * bi_followers_count : 215
     */

    private long id;
    private String uid;//唯一标识
    private String screen_name;//昵称
    private String name;
    private String province;
    private String city;
    private String location;
    private String description;
    private String url;
    private String profile_image_url;
    private String domain;
    private String gender;
    private int followers_count;
    private int friends_count;
    private int statuses_count;
    private int favourites_count;
    private String created_at;
    private boolean following;
    private boolean allow_all_act_msg;
    private boolean geo_enabled;
    private boolean verified;
    private boolean allow_all_comment;
    private String avatar_large;
    private String verified_reason;
    private boolean follow_me;
    private int online_status;
    private int bi_followers_count;

    public long getId() {
        return id;
    }

    public SinaUserInfoVO setId(long id) {
        this.id = id;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public SinaUserInfoVO setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public SinaUserInfoVO setScreen_name(String screen_name) {
        this.screen_name = screen_name;
        return this;
    }

    public String getName() {
        return name;
    }

    public SinaUserInfoVO setName(String name) {
        this.name = name;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public SinaUserInfoVO setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCity() {
        return city;
    }

    public SinaUserInfoVO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public SinaUserInfoVO setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SinaUserInfoVO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public SinaUserInfoVO setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public SinaUserInfoVO setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public SinaUserInfoVO setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public SinaUserInfoVO setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public SinaUserInfoVO setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
        return this;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public SinaUserInfoVO setFriends_count(int friends_count) {
        this.friends_count = friends_count;
        return this;
    }

    public int getStatuses_count() {
        return statuses_count;
    }

    public SinaUserInfoVO setStatuses_count(int statuses_count) {
        this.statuses_count = statuses_count;
        return this;
    }

    public int getFavourites_count() {
        return favourites_count;
    }

    public SinaUserInfoVO setFavourites_count(int favourites_count) {
        this.favourites_count = favourites_count;
        return this;
    }

    public String getCreated_at() {
        return created_at;
    }

    public SinaUserInfoVO setCreated_at(String created_at) {
        this.created_at = created_at;
        return this;
    }

    public boolean isFollowing() {
        return following;
    }

    public SinaUserInfoVO setFollowing(boolean following) {
        this.following = following;
        return this;
    }

    public boolean isAllow_all_act_msg() {
        return allow_all_act_msg;
    }

    public SinaUserInfoVO setAllow_all_act_msg(boolean allow_all_act_msg) {
        this.allow_all_act_msg = allow_all_act_msg;
        return this;
    }

    public boolean isGeo_enabled() {
        return geo_enabled;
    }

    public SinaUserInfoVO setGeo_enabled(boolean geo_enabled) {
        this.geo_enabled = geo_enabled;
        return this;
    }

    public boolean isVerified() {
        return verified;
    }

    public SinaUserInfoVO setVerified(boolean verified) {
        this.verified = verified;
        return this;
    }

    public boolean isAllow_all_comment() {
        return allow_all_comment;
    }

    public SinaUserInfoVO setAllow_all_comment(boolean allow_all_comment) {
        this.allow_all_comment = allow_all_comment;
        return this;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public SinaUserInfoVO setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
        return this;
    }

    public String getVerified_reason() {
        return verified_reason;
    }

    public SinaUserInfoVO setVerified_reason(String verified_reason) {
        this.verified_reason = verified_reason;
        return this;
    }

    public boolean isFollow_me() {
        return follow_me;
    }

    public SinaUserInfoVO setFollow_me(boolean follow_me) {
        this.follow_me = follow_me;
        return this;
    }

    public int getOnline_status() {
        return online_status;
    }

    public SinaUserInfoVO setOnline_status(int online_status) {
        this.online_status = online_status;
        return this;
    }

    public int getBi_followers_count() {
        return bi_followers_count;
    }

    public SinaUserInfoVO setBi_followers_count(int bi_followers_count) {
        this.bi_followers_count = bi_followers_count;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.uid);
        dest.writeString(this.screen_name);
        dest.writeString(this.name);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.location);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.profile_image_url);
        dest.writeString(this.domain);
        dest.writeString(this.gender);
        dest.writeInt(this.followers_count);
        dest.writeInt(this.friends_count);
        dest.writeInt(this.statuses_count);
        dest.writeInt(this.favourites_count);
        dest.writeString(this.created_at);
        dest.writeByte(this.following ? (byte) 1 : (byte) 0);
        dest.writeByte(this.allow_all_act_msg ? (byte) 1 : (byte) 0);
        dest.writeByte(this.geo_enabled ? (byte) 1 : (byte) 0);
        dest.writeByte(this.verified ? (byte) 1 : (byte) 0);
        dest.writeByte(this.allow_all_comment ? (byte) 1 : (byte) 0);
        dest.writeString(this.avatar_large);
        dest.writeString(this.verified_reason);
        dest.writeByte(this.follow_me ? (byte) 1 : (byte) 0);
        dest.writeInt(this.online_status);
        dest.writeInt(this.bi_followers_count);
    }

    public SinaUserInfoVO() {
    }

    protected SinaUserInfoVO(Parcel in) {
        this.id = in.readLong();
        this.uid = in.readString();
        this.screen_name = in.readString();
        this.name = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.location = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.profile_image_url = in.readString();
        this.domain = in.readString();
        this.gender = in.readString();
        this.followers_count = in.readInt();
        this.friends_count = in.readInt();
        this.statuses_count = in.readInt();
        this.favourites_count = in.readInt();
        this.created_at = in.readString();
        this.following = in.readByte() != 0;
        this.allow_all_act_msg = in.readByte() != 0;
        this.geo_enabled = in.readByte() != 0;
        this.verified = in.readByte() != 0;
        this.allow_all_comment = in.readByte() != 0;
        this.avatar_large = in.readString();
        this.verified_reason = in.readString();
        this.follow_me = in.readByte() != 0;
        this.online_status = in.readInt();
        this.bi_followers_count = in.readInt();
    }

    public static final Creator<SinaUserInfoVO> CREATOR = new Creator<SinaUserInfoVO>() {
        @Override
        public SinaUserInfoVO createFromParcel(Parcel source) {
            return new SinaUserInfoVO(source);
        }

        @Override
        public SinaUserInfoVO[] newArray(int size) {
            return new SinaUserInfoVO[size];
        }
    };
}
