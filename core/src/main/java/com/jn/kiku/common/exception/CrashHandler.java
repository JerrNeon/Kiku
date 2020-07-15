package com.jn.kiku.common.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;

import com.jn.common.SPManage;
import com.jn.common.util.LogUtils;
import com.jn.kiku.utils.ImageUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (全局处理异常)
 * @create by: chenwei
 * @date 2016/10/18 10:09
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler sInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息
     *
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            //导出异常信息到手机中
            //dumpExceptionToLocal(ex);
            //这里可以上传异常信息到服务器，便于开发人员分析日志从而解决bug
            uploadExceptionToServer(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ex.printStackTrace();
        //如果系统提供默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            //自己处理
            try {
                //延迟2秒杀进程
                Thread.sleep(2000);
                //showToast("很抱歉，程序出现异常，即将退出~");
            } catch (InterruptedException e) {
                logE("error : " + e.getMessage());
            }
            Process.killProcess(Process.myPid());
        }
    }

    /**
     * 导出异常信息到手机中
     *
     * @param ex
     * @throws IOException
     */
    private void dumpExceptionToLocal(Throwable ex) throws IOException {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date(System.currentTimeMillis()));
        File file = new File(ImageUtil.getCrashPath(time));
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
            logI("dump crash info seccess");
        } catch (Exception e) {
            logE(e.getMessage());
            logE("dump crash info failed");
        }
    }

    /**
     * 手机信息
     *
     * @param pw
     * @throws PackageManager.NameNotFoundException
     */
    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);
        //Android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        //CPU架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    /**
     * 上传异常信息到服务器，便于开发人员分析日志从而解决bug
     */
    private void uploadExceptionToServer(Throwable ex) throws IOException {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date(System.currentTimeMillis()));
        try {
            Writer writer = new StringWriter();
            PrintWriter pw = new PrintWriter(new BufferedWriter(writer));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
            SPManage.getInstance().setExceptionMessage(writer.toString());
            logI("dump crash info seccess");
        } catch (Exception e) {
            logE(e.getMessage());
            logE("dump crash info failed");
        }
    }

    protected void logI(String message) {
        LogUtils.i(getClass().getSimpleName(), message);
    }


    protected void logE(String message) {
        LogUtils.e(getClass().getSimpleName(), message);
    }

}
