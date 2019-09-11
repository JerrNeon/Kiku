package com.jn.kiku.utils.manager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jn.kiku.R;
import com.jn.kiku.annonation.PermissionType;
import com.jn.kiku.dialog.LocationServiceDialogFragment;
import com.jn.kiku.dialog.PermissionDialogFragment;
import com.jn.kiku.utils.IntentUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RxPermissions权限管理)
 * @create by: chenwei
 * @date 2018/5/17 17:39
 */
public class RxPermissionsManager {

    /**
     * 相机权限
     */
    public static final String CAMERA = Manifest.permission.CAMERA;
    /**
     * 存储空间(相册)权限
     */
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    /**
     * 电话权限
     */
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    /**
     * 读取手机状态权限
     */
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    /**
     * 定位权限
     */
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    /**
     * 定位权限
     */
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;


    private static final String[] CAMERA_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA};//相机权限
    private static final String[] WRITE_EXTERNAL_STORAGES_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE};//存储空间(相册)权限
    private static final String[] CALL_PHONES_PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE};//电话权限
    private static final String[] LOCATIONS_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};//定位权限
    private static final String[] CAMERA_WRITE_EXTERNAL_STORAGE_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};//拍照&存储空间权限

    @SuppressLint("CheckResult")
    public static void requestPermission(final Activity activity, RxPermissions rxPermissions, @PermissionType final int permissionType, final String appName, final Consumer<Boolean> consumer) {
        String[] permissions = null;
        String permissionName = null;
        switch (permissionType) {
            case PermissionType.CAMERA:
                permissions = CAMERA_PERMISSIONS;
                permissionName = activity.getResources().getString(R.string.permission_camera);
                break;
            case PermissionType.WRITE_EXTERNAL_STORAGE:
                permissions = WRITE_EXTERNAL_STORAGES_PERMISSIONS;
                permissionName = activity.getResources().getString(R.string.permission_storage);
                break;
            case PermissionType.CALL_PHONE:
                permissions = CALL_PHONES_PERMISSIONS;
                permissionName = activity.getResources().getString(R.string.permission_phone);
                break;
            case PermissionType.LOCATION:
                permissions = LOCATIONS_PERMISSIONS;
                permissionName = activity.getResources().getString(R.string.permission_location);
                break;
            case PermissionType.CAMERA_WRITE_EXTERNAL_STORAGE:
                permissions = CAMERA_WRITE_EXTERNAL_STORAGE_PERMISSIONS;
                permissionName = activity.getResources().getString(R.string.permission_camera_storage);
                break;
            default:
                break;
        }
        final String finalPermissionName = permissionName;
        requestEachCombined(rxPermissions, permissions, new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) {
                try {
                    if (permission.granted) {
                        if (permissionType == PermissionType.LOCATION) {
                            if (IntentUtils.checkLocationSereviceOPenInM(activity.getApplicationContext())) {//6.0以上GPS开启成功
                                if (consumer != null)
                                    consumer.accept(true);//权限申请成功
                            } else {
                                showLocationServiceDialog(activity, consumer);
                            }
                        } else {
                            consumer.accept(true);//权限申请成功
                        }
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        consumer.accept(false);
                    } else {
                        consumer.accept(false);
                        showPermissionDialog(activity, appName, finalPermissionName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    public static void requestEachCombined(RxPermissions rxPermissions, String[] permissions, final Consumer<Permission> consumer) {
        if (rxPermissions == null)
            throw new NullPointerException("RxPermissions not init");
        rxPermissions
                .requestEachCombined(permissions)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (consumer != null)
                            consumer.accept(permission);
                        if (permission.granted) {//所有权限都同意

                        } else if (permission.shouldShowRequestPermissionRationale) {//至少拒绝了一个权限并没有勾选不再提示

                        } else {//至少拒绝了一个权限并勾选了不再提示

                        }
                    }
                });
    }

    /**
     * 请求所有权限
     *
     * @param rxPermissions RxPermissions
     * @param consumer      Consumer
     * @param permissions   所有权限
     *                      <p>
     *                      一般用于启动页
     *                      </P>
     */
    @SuppressLint("CheckResult")
    public static void requestAllPermissions(RxPermissions rxPermissions, final Consumer<Boolean> consumer, String... permissions) {
        if (rxPermissions == null)
            throw new NullPointerException("RxPermissions not init");
        rxPermissions
                .request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        try {
                            if (consumer != null)
                                consumer.accept(aBoolean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 显示权限拒绝并勾选不再提示对话框
     *
     * @param activity       Activity
     * @param appName        App名称
     * @param permissionName 权限名称
     */
    private static void showPermissionDialog(final Activity activity, final String appName, final String permissionName) {
        Bundle bundle = new Bundle();
        bundle.putString(PermissionDialogFragment.APP_NAME, appName);
        bundle.putString(PermissionDialogFragment.PERMISSION_NAME, permissionName);
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            PermissionDialogFragment.newInstance(bundle)
                    .show(appCompatActivity.getSupportFragmentManager(), "");
        }
    }

    /**
     * 显示定位服务未开启对话框
     *
     * @param activity
     * @param consumer
     */
    private static void showLocationServiceDialog(final Activity activity, final Consumer<Boolean> consumer) {
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            LocationServiceDialogFragment.newInstance()
                    .show(appCompatActivity.getSupportFragmentManager(), "", new LocationServiceDialogFragment.ILocationServiceListener() {
                        @Override
                        public void onLocationServiceOpenSuccess() {
                            try {
                                consumer.accept(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLocationServiceOpenFailure() {
                            try {
                                consumer.accept(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

}
