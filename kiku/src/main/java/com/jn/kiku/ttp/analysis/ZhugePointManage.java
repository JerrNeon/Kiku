package com.jn.kiku.ttp.analysis;

import android.content.Context;

import com.jn.kiku.ttp.analysis.callback.IZhuGePoint;

import java.util.HashMap;
import java.util.List;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (诸葛IO埋点)
 * @create by: chenwei
 * @date 2017/6/1 11:54
 */
public class ZhugePointManage implements IZhuGePoint {

    private Context mContext = null;
    private static ZhugePointManage instance = null;
    //private static final String SOURCE = "App-";//来源
    //private static final String SOURCE = "";//来源
    private static final String SOURCE = "APP_a-";//来源

    private ZhugePointManage(Context context) {
        mContext = context;
    }

    public synchronized static ZhugePointManage getInstance(Context context) {
        if (instance == null)
            instance = new ZhugePointManage(context.getApplicationContext());
        return instance;
    }

    @Override
    public void point_watch() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "导航-选表");
    }

    @Override
    public void point_onlinewatch() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "导航-在线购表");
    }

    @Override
    public void point_brands() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "导航-全部腕表");
    }

    @Override
    public void point_jifen() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "导航-积分换礼");
    }

    @Override
    public void point_info() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "导航-腕表资讯");
    }

    @Override
    public void point_stores() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "导航-全国门店");
    }

    @Override
    public void point_maintian() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "导航-维修网点");
    }

    @Override
    public void point_watch_search(String brand, String series, String price, String other) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("品牌", brand);
        map.put("系列", series);
        map.put("价位", price);
        map.put("其它", other);
        ZhugeManage.getInstance().track(mContext, SOURCE + "首页-选表-搜索", map);
    }

    @Override
    public void point_watch_advancedsearch() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "首页-选表-高级搜索");
    }


    @Override
    public void point_product_appointment_choosestore(String province, String city, String street) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("省", province);
        map.put("市", city);
        map.put("门店地址", street);
        ZhugeManage.getInstance().track(mContext, SOURCE + "门店购买-门店选择", map);
    }

    @Override
    public void point_product_appointment_choosestore_closest() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "门店购买-门店选择-最近门店");
    }

    @Override
    public void point_product_appointment_choosestore_closeststore(String storename) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("门店名称", storename);
        ZhugeManage.getInstance().track(mContext, SOURCE + "门店购买-门店选择-最近门店-门店", map);
    }

    @Override
    public void point_product_appointment_choosestore_authorized() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "门店购买-门店选择-授权门店");
    }

    @Override
    public void point_product_appointment_choosestore_authorizedstore(String storename) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("门店名称", storename);
        ZhugeManage.getInstance().track(mContext, SOURCE + "门店购买-门店选择-授权门店-门店", map);
    }

    @Override
    public void point_product_appointment_appointnow() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "门店购买-立即预约");
    }

    @Override
    public void point_product_appointment_onlinechat() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "门店购买-在线交谈");
    }

    @Override
    public void point_cart_checkout(List<String> skulist, List<String> pricelist, List<String> amountlist, String totalprice) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("SKUList", skulist);
        map.put("价格", pricelist);
        map.put("数量", amountlist);
        map.put("总价", totalprice);
        ZhugeManage.getInstance().track(mContext, SOURCE + "购物车-立即结算", map);
    }

    @Override
    public void point_onepage_saveaddress() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "结算页-保存收货信息");
    }

    @Override
    public void point_onepage_placeorder(List<String> skulist, List<String> pricelist, List<String> amountlist, String totalprice) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("SKUList", skulist);
        map.put("价格", pricelist);
        map.put("数量", amountlist);
        map.put("总价", totalprice);
        ZhugeManage.getInstance().track(mContext, SOURCE + "结算页-提交订单", map);
    }

    @Override
    public void point_order_status(String orderid, String grandtotal, String created_at, String status) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("订单ID", orderid);
        map.put("订单金额", grandtotal);
        map.put("订单时间", created_at);
        map.put("订单状态", status);
        ZhugeManage.getInstance().track(mContext, SOURCE + "结算页-订单状态", map);
    }

    @Override
    public void point_kefu(String url) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("当前Url", url);
        ZhugeManage.getInstance().track(mContext, SOURCE + "在线客服", map);
    }

    @Override
    public void point_repairkefu(String url) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("当前Url", url);
        ZhugeManage.getInstance().track(mContext, SOURCE + "维修客服", map);
    }

    @Override
    public void point_register() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "点击注册");
    }

    @Override
    public void point_register_submit(String mobile) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("手机号码", mobile);
        ZhugeManage.getInstance().track(mContext, SOURCE + "点击注册-提交注册信息", map);
    }

    @Override
    public void point_login() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "点击登录");
    }

    @Override
    public void point_login_submit(String mobile) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("手机号码", mobile);
        ZhugeManage.getInstance().track(mContext, SOURCE + "点击登录-登录", map);
    }

    @Override
    public void point_cart() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "点击购物车");
    }

    @Override
    public void point_order() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "点击我的订单");
    }

    @Override
    public void point_order_check(String orderid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("订单号码", orderid);
        ZhugeManage.getInstance().track(mContext, SOURCE + "我的订单-查看订单", map);
    }

    @Override
    public void point_retailstore() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "导航-首页-零售门店查询");
    }

    @Override
    public void point_repairstore() {
        ZhugeManage.getInstance().track(mContext, SOURCE + "导航-维修-维修门店查询");
    }

    @Override
    public void point_retailstore_search(String province, String city) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("省", province);
        map.put("市", city);
        ZhugeManage.getInstance().track(mContext, SOURCE + "零售门店搜索", map);
    }

    @Override
    public void point_repairstore_search(String province, String city) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("省", province);
        map.put("市", city);
        ZhugeManage.getInstance().track(mContext, SOURCE + "维修门店搜索", map);
    }

    @Override
    public void point_retailstore_detail(String storeName, String storeAddress, String storePhone) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("门店名称", storeName);
        map.put("门店地址", storeAddress);
        map.put("门店电话", storePhone);
        ZhugeManage.getInstance().track(mContext, SOURCE + "零售门店详情", map);
    }

    @Override
    public void point_repairstore_detail(String storeName, String storeAddress, String storePhone) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("门店名称", storeName);
        map.put("门店地址", storeAddress);
        map.put("门店电话", storePhone);
        ZhugeManage.getInstance().track(mContext, SOURCE + "维修门店详情", map);
    }

    @Override
    public void point_news_detail(String newsId, String newsType, String newsTitle) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("资讯ID", newsId);
        map.put("资讯分类", newsType);
        map.put("资讯标题", newsTitle);
        ZhugeManage.getInstance().track(mContext, SOURCE + "资讯详情", map);
    }
}
