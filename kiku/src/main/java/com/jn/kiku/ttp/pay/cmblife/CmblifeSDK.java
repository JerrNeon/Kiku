// -------------------------------------------------------------------------------------
// CMB Confidential
//
// Copyright (C) 2015年8月3日 China Merchants Bank Co., Ltd. All rights reserved.
//
// No part of this file may be reproduced or transmitted in any form or by any
// means,
// electronic, mechanical, photocopying, recording, or otherwise, without prior
// written permission of China Merchants Bank Co., Ltd.
//
// -------------------------------------------------------------------------------------
package com.jn.kiku.ttp.pay.cmblife;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Base64;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 掌上生活SDK
 */
public class CmblifeSDK {

    private static final String SDK_VERSION = "1";

    private static final String CMBLIFE_PACKAGE_NAME
            = "com.cmbchina.ccd.pluto.cmbActivity";

    private static final String CMBLIFE_ENTRANCE_ACTIVITY
            = "com.cmbchina.ccd.pluto.cmbActivity.open.OpenSplashActivity";

    private static final String URL_CMBLIFE_REDIRECT
            = "http://cmblife.cmbchina.com/cmblife/download/mchAppRedirect.html";

    private static final String URL_CMBLIFE_DOWNLOAD
            = "http://cmblife.cmbchina.com/cmblife/download/mchAppDownload.html";

    private static final String ACTION_TYPE = "android.intent.action.VIEW";

    private static final String PROTOCOL = "protocol";

    private static final String REQUEST_CODE = "requestCode";

    private static final String RESULT = "result";

    private static final String PACKAGE_NAME = "packageName";

    private static final String CALL_BACK_ACTIVITY = "callBackActivity";

    private static final String CMBLIFE_SIGN =
            "MIICQjCCAasCBB4pSYMwDQYJKoZIhvcNAQEFBQAwZzEQMA4GA1UEAxMHY21ibGlmZTEQMA4GA1UE"
                    + "CxMHVW5rbm93bjEQMA4GA1UEChMHVW5rbm93bjEQMA4GA1UEBxMHVW5rbm93bjEQMA4GA1UECBMH"
                    + "VW5rbm93bjELMAkGA1UEBhMCQ04wIBcNMTIxMTE5MTYwMDAwWhgPMjEwMDExMTkxNjAwMDBaMGcx"
                    + "EDAOBgNVBAMTB2NtYmxpZmUxEDAOBgNVBAsTB1Vua25vd24xEDAOBgNVBAoTB1Vua25vd24xEDAO"
                    + "BgNVBAcTB1Vua25vd24xEDAOBgNVBAgTB1Vua25vd24xCzAJBgNVBAYTAkNOMIGfMA0GCSqGSIb3"
                    + "DQEBAQUAA4GNADCBiQKBgQCXP4lLiYD95wrV0k+2eawqnkTA7WkCt17NaBGTJzBYSfFKerD65D0t"
                    + "TXKWe5GmST/+ckfOSnhXQK2Mk5euvOEAJqHkW83WIXBx5WZAkenUvm0d4y7vnbAjPtIDmEAZdsIK"
                    + "WhVE/qfXSi2Phu00xENZ4uXWPiADm37wGMR2sBp64wIDAQABMA0GCSqGSIb3DQEBBQUAA4GBADsx"
                    + "jU5EPWfi/J2Ju6BceL0JVzBKTj0MDLDMsfyH3qkVwcNN2ZLXaX5ik2IinVc3FytvptrDSp9sKHzB"
                    + "o33yvvjhLTtFPs1TWa60VaUmwODnFAuOnnus0vb0YybtP73EeJRH3dGwcI18pfSAutenhl4HEWdH"
                    + "mZNCxffPQ+cqI3Wy";

    private static final String TIPS_1 = "您的掌上生活版本过低或未能正确安装，请从官方渠道重新下载";

    private static final String TIPS_2 = "您的掌上生活可能已被篡改，请从官方渠道重新下载";

    /**
     * 获取掌上生活SDK版本
     *
     * @return
     */
    public static String getVersion() {
        return SDK_VERSION;
    }

    /**
     * 判断掌上生活是否安装
     *
     * @param context
     * @return
     */
    public static boolean isInstall(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo(CMBLIFE_PACKAGE_NAME, 0);
            return true;
        } catch (Exception e) {
            // 若发生异常，则认为没有安装
            return false;
        }
    }

    /**
     * 跳转至下载页面下载掌上生活
     *
     * @param context
     */
    public static void downloadCmblife(Context context) {
        startWebExplorer(context, URL_CMBLIFE_DOWNLOAD);
    }

    /**
     * 重定向至浏览器
     *
     * @param context
     */
    private static void startRedirectPage(Context context, String protocol) {
        startWebExplorer(context, URL_CMBLIFE_REDIRECT + "?protocol=" + URLEncoder.encode(protocol));
    }

    /**
     * 唤起浏览器
     *
     * @param context
     * @param url
     */
    private static void startWebExplorer(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(ACTION_TYPE);
        Uri contentUrl = Uri.parse(url);
        intent.setData(contentUrl);
        context.startActivity(intent);
    }

    /**
     * 唤起掌上生活
     *
     * @param context
     * @param protocol
     * @param callBackActivity
     */
    public static String startCmblife(Context context,
                                      String protocol, Class<?> callBackActivity, String requestCode) {

//		String sign = getPubKey(context);
//		if(null != sign) {
//			// 已安装
//			if(CMBLIFE_SIGN.equals(sign)) {
//				// 公钥验证成功
        try {
            Intent intent = new Intent();
            ComponentName cn = new ComponentName(CMBLIFE_PACKAGE_NAME, CMBLIFE_ENTRANCE_ACTIVITY);
            intent.setComponent(cn);
            intent.putExtra(PACKAGE_NAME, context.getPackageName());
            intent.putExtra(CALL_BACK_ACTIVITY, callBackActivity.getName());
            intent.putExtra(PROTOCOL, protocol);
            intent.putExtra(REQUEST_CODE, requestCode);
            intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return "";
        } catch (Exception e) {
            e.printStackTrace();
//					Toast.makeText(context, TIPS_1, Toast.LENGTH_LONG).show();
            //startRedirectPage(context, protocol);
            return TIPS_1;
        }
//			} else {
//				// 公钥验证失败
//				Toast.makeText(context, TIPS_2, Toast.LENGTH_LONG).show();
//				return TIPS_2;
//			}
//		} else {
//			// 未安装，打开浏览器
//			startRedirectPage(context, protocol);
//			return "";
//		}
//		if(isInstall(context)) {
//			// 已安装
//			if(validatePubKey(context)) {
//				try {
//					Intent intent = new Intent();
//					ComponentName cn = new ComponentName(CMBLIFE_PACKAGE_NAME, CMBLIFE_ENTRANCE_ACTIVITY);
//					intent.setComponent(cn);
//					intent.putExtra(PACKAGE_NAME, context.getPackageName());
//					intent.putExtra(CALL_BACK_ACTIVITY, callBackActivity.getName());
//					intent.putExtra(PROTOCOL, protocol);
//					intent.putExtra(REQUEST_CODE, requestCode);
//					context.startActivity(intent);
//					return "";
//				} catch(Exception e) {
//					Toast.makeText(context, TIPS_1, Toast.LENGTH_LONG).show();
//					return TIPS_1;
//				}
//			} else {
//				Toast.makeText(context, TIPS_2, Toast.LENGTH_LONG).show();
//				return TIPS_2;
//			}
//		} else {
//			// 未安装，打开浏览器
//			startRedirectPage(context, protocol);
//			return "";
//		}
    }

    /**
     * 处理掌上生活回调逻辑
     *
     * @param listener
     * @param intent
     */
    public static void handleCallBack(ICmblifeListener listener, Intent intent) throws Exception {
        if (null == listener || null == intent) {
            return;
        }
        String result = intent.getStringExtra(RESULT);
        String requestCode = intent.getStringExtra(REQUEST_CODE);
        if (null != result && !"".equals(result)) {
            Map<String, String> resultMap = jsonStringToMap(result);
            if (null != resultMap) {
                listener.onCmblifeCallBack(requestCode, resultMap);
            }
        }
    }

    /**
     * json报文转map
     *
     * @param jsonString
     * @return
     */
    private static Map<String, String> jsonStringToMap(String jsonString)
            throws Exception {
        JSONObject jsonObject = new JSONObject(jsonString);
        Map<String, String> resultMap = new HashMap<String, String>();
        Iterator<String> iterator = jsonObject.keys();
        String key = null;
        String value = null;
        while (iterator.hasNext()) {
            key = iterator.next();
            value = jsonObject.getString(key);
            resultMap.put(key, value);
        }
        return resultMap;
    }

    /**
     * 获取掌上生活公钥
     *
     * @param context
     * @return
     */
    private static String getPubKey(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(CMBLIFE_PACKAGE_NAME, PackageManager.GET_SIGNATURES);
            String sign = Base64.encodeToString(info.signatures[0].toByteArray(), Base64.DEFAULT);
            sign = sign.replace("\n", "");
            return sign;
        } catch (Exception e) {
            return null;
        }
    }

//	/**
//	 * 校验掌上生活公钥
//	 * 
//	 * @return
//	 */
//	private static boolean validatePubKey(Context context) {
//		try {
//			PackageManager pm = context.getPackageManager();
//			PackageInfo info = pm.getPackageInfo(CMBLIFE_PACKAGE_NAME, PackageManager.GET_SIGNATURES);
//			String sign = Base64.encodeToString(info.signatures[0].toByteArray(), Base64.DEFAULT);
//			sign = sign.replace("\n","");
//			return sign.equals(CMBLIFE_SIGN);
//		} catch (NameNotFoundException e) {
//			return false;
//		}
//	}

}

