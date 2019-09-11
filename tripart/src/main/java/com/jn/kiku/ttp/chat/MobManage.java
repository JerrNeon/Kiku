package com.jn.kiku.ttp.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.callback.ValueCallBack;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.jn.kiku.ttp.BuildConfig;
import com.jn.kiku.ttp.TtpConstants;

import java.util.List;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (环信管理)
 * @create by: chenwei
 * @date 2017/10/17 10:59
 */
public class MobManage {

    private static MobManage instance = null;

    private MobManage() {
    }

    public synchronized MobManage getInstance() {
        if (instance == null)
            instance = new MobManage();
        return instance;
    }

    public void init(Context context) {
        init(context, BuildConfig.DEBUG);
    }

    public void init(Context context, boolean logEnable) {
        ChatClient.getInstance().init(context, new ChatClient.Options()
                .setAppkey(TtpConstants.MOB_APPKEY)//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“Ap
                .setTenantId(TtpConstants.MOB_TENANTID)//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”
                .setConsoleLog(logEnable));//是否开启日志
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     */
    public void register(String username, String password) {
        ChatClient.getInstance().register(username, password, new Callback() {
            @Override
            public void onSuccess() {

            }

            /*ErrorCode:
                    Error.NETWORK_ERROR 网络不可用
                    Error.USER_ALREADY_EXIST  用户已存在
                    Error.USER_AUTHENTICATION_FAILED 无开放注册权限（后台管理界面设置[开放|授权]）
                    Error.USER_ILLEGAL_ARGUMENT 用户名非法
           */
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    public void login(String username, String password) {
        ChatClient.getInstance().login(username, password, new Callback() {
            @Override
            public void onSuccess() {

            }

            /*ErrorCode:
                    Error.NETWORK_ERROR 网络不可用
                    Error.USER_ALREADY_EXIST  用户已存在
                    Error.USER_AUTHENTICATION_FAILED 无开放注册权限（后台管理界面设置[开放|授权]）
                    Error.USER_ILLEGAL_ARGUMENT 用户名非法
           */
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param token    token
     */
    public void loginWithToken(String username, String token) {
        ChatClient.getInstance().loginWithToken(username, token, new Callback() {
            @Override
            public void onSuccess() {

            }

            /*ErrorCode:
                    Error.NETWORK_ERROR 网络不可用
                    Error.USER_ALREADY_EXIST  用户已存在
                    Error.USER_AUTHENTICATION_FAILED 无开放注册权限（后台管理界面设置[开放|授权]）
                    Error.USER_ILLEGAL_ARGUMENT 用户名非法
           */
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 退出登录
     */
    public void logout() {
        // unbindToken:是否解绑推送的devicetoken
        ChatClient.getInstance().logout(true, new Callback() {
            @Override
            public void onSuccess() {

            }

            /*ErrorCode:
                    Error.NETWORK_ERROR 网络不可用
                    Error.USER_ALREADY_EXIST  用户已存在
                    Error.USER_AUTHENTICATION_FAILED 无开放注册权限（后台管理界面设置[开放|授权]）
                    Error.USER_ILLEGAL_ARGUMENT 用户名非法
           */
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 是否已经登录
     *
     * @return true:登录
     */
    public boolean isLogin() {
        return ChatClient.getInstance().isLoggedInBefore();
    }

    /**
     * 打开客服界面
     *
     * @param activity       Activity
     * @param toChatUsername 客服
     */
    public void openServiceUI(Activity activity, String toChatUsername) {
        Intent intent = new IntentBuilder(activity)
                .setServiceIMNumber(toChatUsername)//客服关联的IM服务号 获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“IM服务号”
                .build();
        activity.startActivity(intent);
    }

    /**
     * 创建一个新的留言
     *
     * @param postContent 留言内容
     * @param projectId   留言ProjectId  进入“管理员模式 → 留言”，可以看到这个Project ID
     * @param imUser      接入环信移动客服系统使用的关联的IM服务号
     */
    public void createLeaveMsg(String postContent, String projectId, String imUser) {
        ChatClient.getInstance().leaveMsgManager().createLeaveMsg(postContent, projectId, imUser, new ValueCallBack<String>() {
            @Override
            public void onSuccess(String o) {

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 添加网络监听，可以显示当前是否连接服务器
     */
    public void addConnectionListener() {
        ChatClient.getInstance().addConnectionListener(new ChatClient.ConnectionListener() {
            @Override
            public void onConnected() {
                //成功连接到服务器
            }

            /*  errorcode的值
                    Error.USER_REMOVED 账号移除
                    Error.USER_LOGIN_ANOTHER_DEVICE 账号在其他地方登录
                    Error.USER_AUTHENTICATION_FAILED 账号密码错误
                    Error.USER_NOT_FOUND  账号找不到
            */
            @Override
            public void onDisconnected(int errorcode) {


            }
        });
    }

    /**
     * 添加消息监听
     */
    public void addMessageListener() {
        ChatClient.getInstance().chatManager().addMessageListener(new ChatManager.MessageListener() {
            @Override
            public void onMessage(List<Message> list) {
                //收到普通消息
            }

            @Override
            public void onCmdMessage(List<Message> list) {
                //收到命令消息，命令消息不存数据库，一般用来作为系统通知，例如留言评论更新，
                //会话被客服接入，被转接，被关闭提醒
            }

            @Override
            public void onMessageStatusUpdate() {
                //消息的状态修改，一般可以用来刷新列表，显示最新的状态
            }

            @Override
            public void onMessageSent() {
                //发送消息后，会调用，可以在此刷新列表，显示最新的消息
            }
        });
    }
}
