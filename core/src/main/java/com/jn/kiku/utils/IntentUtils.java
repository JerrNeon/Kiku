package com.jn.kiku.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.fragment.app.Fragment;
import android.telephony.TelephonyManager;

import java.io.File;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (打开系统相关服务管理类)
 * @create by: chenwei
 * @date 2016/12/2 11:40
 */
public class IntentUtils {

    /**
     * 打开照相机(Activity)
     *
     * @param mContext      activity
     * @param cameraImgPath 拍照存储路径
     * @param requestCode   请求码
     */
    public static void openCamera(Activity mContext, String cameraImgPath, int requestCode) {
        try {
            File file = new File(cameraImgPath);
            file.deleteOnExit();
            Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                photoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri picUri = UriUtils.getUri(mContext.getApplicationContext(), cameraImgPath);
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            mContext.startActivityForResult(photoIntent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开照相机(Fragment)
     *
     * @param mContext      fragment
     * @param cameraImgPath 拍照存储路径
     * @param requestCode   请求码
     */
    public static void openCamera(Fragment mContext, String cameraImgPath, int requestCode) {
        try {
            File file = new File(cameraImgPath);
            file.deleteOnExit();
            Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                photoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri picUri = UriUtils.getUri(mContext.getActivity().getApplicationContext(), cameraImgPath);
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
            mContext.startActivityForResult(photoIntent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开相册(Activity)
     *
     * @param mContext    activity
     * @param requestCode 请求码
     */
    public static void openAlbum(Activity mContext, int requestCode) {
        try {
            Intent iconIntent = new Intent(Intent.ACTION_GET_CONTENT);
            iconIntent.addCategory(Intent.CATEGORY_OPENABLE);
            iconIntent.setType("image/*");
            String[] mimeTypes = {"image/jpg", "image/jpeg", "image/png"};
            iconIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            mContext.startActivityForResult(iconIntent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开相册(Fragment)
     *
     * @param mContext    fragment
     * @param requestCode 请求码
     */
    public static void openAlbum(Fragment mContext, int requestCode) {
        try {
            Intent iconIntent = new Intent(Intent.ACTION_GET_CONTENT);
            iconIntent.addCategory(Intent.CATEGORY_OPENABLE);
            iconIntent.setType("image/*");
            String[] mimeTypes = {"image/jpg", "image/jpeg", "image/png"};
            iconIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            mContext.startActivityForResult(iconIntent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片
     *
     * @param mContext    activity
     * @param imgPath     要裁剪图片路径
     * @param targetPath  裁剪后路径
     * @param requestCode 请求码
     */
    public static void cropImage(Activity mContext, String imgPath, String targetPath, int requestCode) {
        File file = new File(targetPath);
        file.deleteOnExit();
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri imgUri = UriUtils.getUri(mContext.getApplicationContext(), imgPath);
        Uri targetUri = Uri.fromFile(new File(targetPath));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra("noFaceDetection", true);//去除默认的人脸识别，否则和剪裁匡重叠
        }
        intent.setDataAndType(imgUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", "PNG");// 返回格式
        intent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);//裁剪后的路径
        mContext.startActivityForResult(intent, requestCode);
    }

    /**
     * 裁剪图片
     *
     * @param mContext    fragment
     * @param imgPath     裁剪路径
     * @param requestCode 请求码
     */
    public static void cropImage(Fragment mContext, String imgPath, String targetPath, int requestCode) {
        File file = new File(targetPath);
        file.deleteOnExit();
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri imgUri = UriUtils.getUri(mContext.getActivity().getApplicationContext(), imgPath);
        Uri targetUri = Uri.fromFile(new File(targetPath));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra("noFaceDetection", true);//去除默认的人脸识别，否则和剪裁匡重叠
        }
        intent.setDataAndType(imgUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", "PNG");// 返回格式
        intent.putExtra(MediaStore.EXTRA_OUTPUT, targetUri);//裁剪后的路径
        mContext.startActivityForResult(intent, requestCode);
    }

    /**
     * 拨打电话
     *
     * @param mContext activity
     */
    @SuppressWarnings("MissingPermission")
    public static void callPhone(Activity mContext, String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拨打电话
     *
     * @param mContext fragment
     */
    @SuppressWarnings("MissingPermission")
    public static void callPhone(Fragment mContext, String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开此app系统设置界面
     *
     * @param mContext context
     */
    public static void openApplicationSetting(Context mContext) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 是否有SIM卡
     * <p>
     * 使用这个方法需要申请 Manifest.permission.READ_PHONE_STATE 这个权限
     *
     * @param mContext
     * @return
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static boolean exitSimCard(Context mContext) {
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
            String simSer = tm != null ? tm.getSimSerialNumber() : null;
            if (simSer == null || simSer.equals("")) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 安装
     *
     * @param mContext 接收外部传进来的context
     */
    public static void install(Context mContext, String apkPath) {
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        Uri apkUri = UriUtils.getUri(mContext.getApplicationContext(), apkPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //区别于 FLAG_GRANT_READ_URI_PERMISSION 跟 FLAG_GRANT_WRITE_URI_PERMISSION， URI权限会持久存在即使重启，直到明确的用 revokeUriPermission(Uri, int) 撤销。
            // 这个flag只提供可能持久授权。但是接收的应用必须调用ContentResolver的takePersistableUriPermission(Uri, int)方法实现
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    /**
     * 安装
     *
     * @param mContext 接收外部传进来的context
     */
    public static Intent getInstallIntent(Context mContext, String apkPath) {
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        Uri apkUri = UriUtils.getUri(mContext.getApplicationContext(), apkPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //区别于 FLAG_GRANT_READ_URI_PERMISSION 跟 FLAG_GRANT_WRITE_URI_PERMISSION， URI权限会持久存在即使重启，直到明确的用 revokeUriPermission(Uri, int) 撤销。
            // 这个flag只提供可能持久授权。但是接收的应用必须调用ContentResolver的takePersistableUriPermission(Uri, int)方法实现
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 获取打开某个Activity的Intent
     *
     * @param mContext 接收外部传进来的context
     */
    public static Intent getInstallIntent(Context mContext, Class<?> cls) {
        return new Intent(mContext, cls).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 卸载
     *
     * @param mContext 接收外部传进来的context
     */
    public static void uninstall(Context mContext, String packname) {
        Uri packageURI = Uri.parse("package:" + packname);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        mContext.startActivity(uninstallIntent);
    }

    /**
     * 判断是否开启定位服务
     *
     * @param context
     * @return true 表示开启
     */
    public static boolean checkLocationSereviceOPen(Context context) {
        LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 6.0系统判断是否开启定位服务
     *
     * @param context
     * @return true 表示开启
     */
    public static boolean checkLocationSereviceOPenInM(Context context) {
        //6.0系统并且定位服务未开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !IntentUtils.checkLocationSereviceOPen(context))
            return false;
        return true;
    }

    /**
     * 打开定位服务界面
     *
     * @param activity
     */
    public static void openLocationService(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(intent, requestCode); // 设置完成后返回到原来的界面
    }

    /**
     * 打开定位服务界面
     *
     * @param fragment
     */
    public static void openLocationService(Fragment fragment, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        fragment.startActivityForResult(intent, requestCode); // 设置完成后返回到原来的界面
    }
}
