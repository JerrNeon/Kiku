package com.jn.kiku.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.jn.kiku.R;
import com.jn.kiku.entiy.VersionUpdateVO;
import com.jn.kiku.net.RetrofitManage;
import com.jn.kiku.receiver.VersionUpdateReceiver;
import com.jn.kiku.utils.FileIOUtils;
import com.jn.kiku.utils.FileTypeUtils;
import com.jn.kiku.utils.ImageUtil;
import com.jn.kiku.utils.IntentUtils;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;

/**
 * Author：Stevie.Chen Time：2019/9/29
 * Class Comment：版本更新服务
 */
public class VersionUpdateService extends Service {

    private static final String CHANNEL_ID = VersionUpdateService.class.getSimpleName();//渠道ID

    protected Notification mNotification = null;//下载任务栏通知框
    private float mCurrentProgress = 0;//下载进度

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        VersionUpdateVO versionUpdateVO = intent.getParcelableExtra(VersionUpdateVO.class.getSimpleName());
        downloadFile(versionUpdateVO);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载文件
     */
    private void downloadFile(final VersionUpdateVO versionUpdateVO) {
        final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = getString(R.string.app_name);
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
            //notificationChannel.enableLights(true);//是否在桌面icon右上角展示小红点
            //notificationChannel.setLightColor(Color.GREEN);//小红点颜色
            if (manager != null) {
                manager.createNotificationChannel(notificationChannel);
            }
        }
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
        builder.setSmallIcon(versionUpdateVO.getAppIconResId());
        builder.setContentTitle(versionUpdateVO.getAppName());
        builder.setTicker(getResources().getString(R.string.versionUpdate_downloadStart));
        final String downLoadFileName = versionUpdateVO.getAppName();
        RetrofitManage.getInstance()
                .getDownloadObservable(versionUpdateVO.getDownLoadUrl(), (progressBytes, totalBytes, progressPercent, done) -> {
                    //计算每百分之5刷新一下通知栏
                    float progress2 = progressPercent * 100;
                    if (progress2 - mCurrentProgress > 5) {
                        mCurrentProgress = progress2;
                        builder.setContentText(String.format(getResources().getString(R.string.versionUpdate_downloadProgress), (int) progress2));
                        builder.setProgress(100, (int) progress2, false);
                        if (manager != null) {
                            manager.notify(0, builder.build());
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(responseBody -> {
                    MediaType mediaType = responseBody.contentType();
                    String mimeType = "";
                    if (mediaType != null) {
                        mimeType = mediaType.type() + File.separator + mediaType.subtype();
                    }
                    String fileSuffix = FileTypeUtils.getFileSuffix(mimeType);//文件后缀名
                    String filePath = ImageUtil.getFileCacheFile().getAbsolutePath() + File.separator + downLoadFileName + "." + fileSuffix;
                    FileIOUtils.writeFileFromIS(filePath, responseBody.byteStream());
                    return filePath;
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        builder.setContentText(String.format(getResources().getString(R.string.versionUpdate_downloadProgress), 0));
                        mCurrentProgress = 0;
                        mNotification = builder.build();
                        mNotification.vibrate = new long[]{500, 500};
                        mNotification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
                        if (manager != null) {
                            manager.notify(0, mNotification);
                        }
                    }

                    @Override
                    public void onNext(String filePath) {
                        Intent intent = IntentUtils.getInstallIntent(VersionUpdateService.this, filePath);
                        PendingIntent pendingIntent = PendingIntent.getActivity(VersionUpdateService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(pendingIntent);
                        builder.setAutoCancel(true);//设置点击后消失
                        builder.setContentText(getResources().getString(R.string.versionUpdate_downloadComplete));
                        builder.setProgress(100, 100, false);
                        if (manager != null) {
                            manager.notify(0, builder.build());
                        }
                        Intent broadcastIntent = new Intent(VersionUpdateReceiver.VERSION_UPDATE_ACTION);
                        broadcastIntent.putExtra(VersionUpdateReceiver.VERSION_UPDATE_ACTION, getResources().getString(R.string.versionUpdate_downloadComplete));
                        sendBroadcast(broadcastIntent);
                        startActivity(intent);
                        stopSelf();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mCurrentProgress = 0;
                        builder.setContentText(getResources().getString(R.string.versionUpdate_downloadFailure));
                        if (manager != null) {
                            manager.notify(0, builder.build());
                        }
                        Intent broadcastIntent = new Intent(VersionUpdateReceiver.VERSION_UPDATE_ACTION);
                        broadcastIntent.putExtra(VersionUpdateReceiver.VERSION_UPDATE_ACTION, getResources().getString(R.string.versionUpdate_downloadFailure));
                        sendBroadcast(broadcastIntent);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
