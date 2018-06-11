package com.jn.kiku.ttp.analysis.callback;

import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (诸葛IO埋点)
 * @create by: chenwei
 * @date 2017/6/1 11:35
 */
public interface IZhuGePoint {

    /**
     * censh-导航-选表
     */
    void point_watch();

    /**
     * censh-导航-在线购表
     */
    void point_onlinewatch();

    /**
     * censh-导航-全部腕表
     */
    void point_brands();

    /**
     * censh-导航-积分换礼
     */
    void point_jifen();

    /**
     * censh-导航-腕表资讯
     */
    void point_info();

    /**
     * censh-导航-全国门店
     */
    void point_stores();

    /**
     * censh-导航-维修网点
     */
    void point_maintian();

    /**
     * censh-首页-选表-搜索
     *
     * @param brand  品牌
     * @param series '系列'
     * @param price  '价位'
     * @param other  '其他'
     */
    void point_watch_search(String brand, String series, String price, String other);

    /**
     * censh-首页-选表-高级搜索
     */
    void point_watch_advancedsearch();

    /**
     * censh-门店购买-门店选择
     *
     * @param province '省'
     * @param city     '市'
     * @param street   '门店地址'
     */
    void point_product_appointment_choosestore(String province, String city, String street);

    /**
     * censh-门店购买-门店选择-最近门店
     */
    void point_product_appointment_choosestore_closest();

    /**
     * censh-门店购买-门店选择-最近门店-门店
     *
     * @param storename '门店名称'
     */
    void point_product_appointment_choosestore_closeststore(String storename);

    /**
     * censh-门店购买-门店选择-授权门店
     */
    void point_product_appointment_choosestore_authorized();

    /**
     * censh-门店购买-门店选择-授权门店-门店
     *
     * @param storename '门店名称'
     */
    void point_product_appointment_choosestore_authorizedstore(String storename);

    /**
     * censh-门店购买-立即预约
     */
    void point_product_appointment_appointnow();

    /**
     * censh-门店购买-在线交谈
     */
    void point_product_appointment_onlinechat();

    /**
     * censh-购物车-立即结算
     *
     * @param skulist    'SKUList'
     * @param pricelist  '价格'
     * @param amountlist '数量'
     * @param totalprice '总价'
     */
    void point_cart_checkout(List<String> skulist, List<String> pricelist, List<String> amountlist, String totalprice);

    /**
     * censh-结算页-保存收货信息
     */
    void point_onepage_saveaddress();

    /**
     * censh-结算页-提交订单
     *
     * @param skulist    'SKUList'
     * @param pricelist  '价格'
     * @param amountlist '数量'
     * @param totalprice '总价'
     */
    void point_onepage_placeorder(List<String> skulist, List<String> pricelist, List<String> amountlist, String totalprice);

    /**
     * censh-结算页-订单状态
     *
     * @param orderid    '订单ID'
     * @param grandtotal '订单金额'
     * @param created_at '订单时间'
     * @param status     '订单状态'
     */
    void point_order_status(String orderid, String grandtotal, String created_at, String status);

    /**
     * censh-在线客服
     *
     * @param url '当前Url'
     */
    void point_kefu(String url);

    /**
     * censh-维修客服
     *
     * @param url '当前Url'
     */
    void point_repairkefu(String url);

    /**
     * censh-点击注册
     */
    void point_register();

    /**
     * censh-点击注册-提交注册信息
     *
     * @param mobile '手机号码'
     */
    void point_register_submit(String mobile);

    /**
     * censh-点击登录
     */
    void point_login();

    /**
     * censh-点击登录-登录
     *
     * @param mobile '手机号码'
     */
    void point_login_submit(String mobile);

    /**
     * censh-点击购物车
     */
    void point_cart();

    /**
     * censh-点击我的订单
     */
    void point_order();

    /**
     * censh-我的订单-查看订单
     *
     * @param orderid '订单号码'
     */
    void point_order_check(String orderid);

    /**
     * censh-导航-首页-零售门店查询
     */
    void point_retailstore();

    /**
     * censh-导航-维修-维修门店查询
     */
    void point_repairstore();

    /**
     * censh-零售门店搜索
     *
     * @param province
     * @param city
     */
    void point_retailstore_search(String province, String city);

    /**
     * censh-维修门店搜索
     *
     * @param province
     * @param city
     */
    void point_repairstore_search(String province, String city);

    /**
     * censh-零售门店详情
     *
     * @param storeName
     * @param storeAddress
     * @param storePhone
     */
    void point_retailstore_detail(String storeName, String storeAddress, String storePhone);

    /**
     * censh-维修门店详情
     *
     * @param storeName
     * @param storeAddress
     * @param storePhone
     */
    void point_repairstore_detail(String storeName, String storeAddress, String storePhone);

    /**
     * censh-资讯详情
     *
     * @param newsId
     * @param newsType
     * @param newsTitle
     */
    void point_news_detail(String newsId, String newsType, String newsTitle);

}
