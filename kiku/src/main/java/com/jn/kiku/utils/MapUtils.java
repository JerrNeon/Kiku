package com.jn.kiku.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (地图工具类)
 * @create by: chenwei
 * @date 2017/7/26 16:37
 */
public class MapUtils {

    private static final String TAG = "MapUtils: ";

    public static final String BAIDU_PACKAGENAME = "com.baidu.BaiduMap";
    public static final String AUTONAVI_PACKAGENAME = "com.autonavi.minimap";
    public static final String GOOGLE_PACKAGENAME = "com.google.android.apps.maps";

    public enum MapType {
        DRIVING, TRANSIT, BIKING, WALKING
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isInstall(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 开启百度地图并导航
     *
     * @param context
     * @param mapType       行驶类型
     * @param originLl      出发地经纬度
     * @param destinationLl 目的地经纬度
     */
    public static void openBaiduMap(Context context, MapType mapType, LatLng originLl, LatLng destinationLl) {
        if (!isInstall(context, BAIDU_PACKAGENAME)) {//未安装
            //market为路径，id为包名
            //显示手机上所有的market商店
            Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
            return;
        }
        try {
            Intent intent = new Intent();
            String uriString = "";
            switch (mapType) {
                case DRIVING:
                    uriString = String.format("baidumap://map/navi?location=%s,%s",
                            destinationLl.latitude + "", destinationLl.longitude + "");
                    break;
                case TRANSIT:
                    uriString = String.format("baidumap://map/direction?destination=%s,%s&mode=transit&target=1",
                            //originLl.latitude + "", originLl.longitude + "",
                            destinationLl.latitude + "", destinationLl.longitude + "");
                    break;
                case BIKING:
                    uriString = String.format("baidumap://map/bikenavi?origin=%s,%s&destination=%s,%s",
                            originLl.latitude + "", originLl.longitude + "",
                            destinationLl.latitude + "", destinationLl.longitude + "");
                    break;
                case WALKING:
                    uriString = String.format("baidumap://map/walknavi?origin=%s,%s&destination=%s,%s",
                            originLl.latitude + "", originLl.longitude + "",
                            destinationLl.latitude + "", destinationLl.longitude + "");
                    break;
            }
            intent.setData(Uri.parse(uriString));
            context.startActivity(intent); //启动调用
        } catch (Exception e) {
            LogUtils.e(TAG + e.getMessage());
        }
    }

    public static void openAutoNaviMap(Context context, MapType mapType, LatLng originLl, LatLng destinationLl) {
        if (!isInstall(context, AUTONAVI_PACKAGENAME)) {
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse("androidamap://navi?sourceApplication=慧医" +
                    "&poiname=我的目的地" +
                    "&lat=" + destinationLl.latitude + "&lon=" + destinationLl.longitude + "&dev=0"));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtils.e(TAG + e.getMessage());
        }
    }

    public static void openGoogleMap(Context context, MapType mapType, LatLng originLl, LatLng destinationLl) {
        if (isInstall(context, GOOGLE_PACKAGENAME)) {
            Toast.makeText(context, "您尚未安装谷歌地图", Toast.LENGTH_LONG).show();
            Uri uri = Uri.parse("market://details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
            return;
        }
        Uri gmmIntentUri = Uri.parse("google.navigation:q="
                + destinationLl.latitude + "," + destinationLl.longitude
                + ", + Sydney +Australia");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }

    private static class LatLng {
        public double latitude;
        public double longitude;
    }
}
