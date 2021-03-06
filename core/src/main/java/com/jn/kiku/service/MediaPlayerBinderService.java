package com.jn.kiku.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.jn.kiku.BuildConfig;

/**
 * Author：Stevie.Chen Time：2019/7/16
 * Class Comment：音乐播放服务
 */
public class MediaPlayerBinderService extends Service {

    private static final String TAG = MediaPlayerBinderService.class.getSimpleName();
    private static final int MSG_CODE = 0x01;
    private MediaPlayer mediaPlayer;//音乐播放关键类
    private WifiManager.WifiLock wifiLock;//保持音乐于后台播放的lock

    private String mMusicUrl = "";//音乐播放地址
    private boolean isPause = false;//是否暂停
    private IPlayProgressListener mIPlayProgressListener;//进度监听
    private Thread mRecordProgressThread;//记录进度的线程
    private AudioManager mAudioManager;//音频管理器

    private Handler mHandler = new Handler(msg -> {
        if (msg.what == MSG_CODE) {
            if (mIPlayProgressListener != null) {
                float progress = (float) msg.obj;
                mIPlayProgressListener.onProgress(progress);
            }
            return true;
        }
        return false;
    });

    public static void bindService(Context context, String musicUrl, ServiceConnection serviceConnection) {
        if (serviceConnection != null) {
            Intent intent = new Intent(context, MediaPlayerBinderService.class);
            intent.putExtra(String.class.getSimpleName(), musicUrl);
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public static void unbindService(Context context, ServiceConnection serviceConnection) {
        if (serviceConnection != null)
            context.unbindService(serviceConnection);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        logD("onCreate");
        // 初始化MediaPlayer
        initMediaPlayer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        logD("onBind");
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (intent != null)
            mMusicUrl = intent.getStringExtra(String.class.getSimpleName());
        return mediaPlayerBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        logD("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logD("onDestroy");
        destroy();
        // 停止服务
        stopSelf();
    }

    // 初始化MediaPlayer
    private void initMediaPlayer() {
        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();

        // 设置音量，参数分别表示左右声道声音大小，取值范围为0~1
        //mediaPlayer.setVolume(0.5f, 0.5f);

        //设置属性
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        mediaPlayer.setAudioAttributes(audioAttributes);

        // 设置是否循环播放
        mediaPlayer.setLooping(false);

        // 设置设备进入锁状态模式-可在后台播放或者缓冲音乐-CPU一直工作
        mediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
//        // 当播放的时候一直让屏幕变亮
//        mediaPlayer.setScreenOnWhilePlaying(true);

        // 如果你使用wifi播放流媒体，你还需要持有wifi锁
        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (manager != null) {
            wifiLock = manager.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, "wifilock");
        }
        if (wifiLock != null)
            wifiLock.acquire();

        mediaPlayer.setOnPreparedListener(mp -> {
            mediaPlayer.start();
            isPause = false;
            if (mIPlayProgressListener != null)
                mIPlayProgressListener.onPrepared(mediaPlayer);
        });
        // 设置播放错误监听
        mediaPlayer.setOnErrorListener((mediaPlayer, i, i1) -> {
            mediaPlayer.reset();
            return false;
        });
        //解决部分手机上每次播放音频时，就触发onCompletion()方法，导致执行destroy方法，关闭服务
        // 设置OnErrorListener，将onError()返回true即可。
        // 在源码中可以查到MediaPlayer的handle中播放异常时，会检测OnErrorListener对象是否为空，如果为空就不会去调用onCompletion()方法了
        mediaPlayer.setOnErrorListener((mediaPlayer, i, i1) -> true);
        // 设置播放完成监听
        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            if (mIPlayProgressListener != null)
                mIPlayProgressListener.onComplete(mediaPlayer);
            destroy();
            stopSelf();
        });
    }

    public void setOnProgressListener(IPlayProgressListener listener) {
        mIPlayProgressListener = listener;
    }

    // 播放
    public void play() {
        try {
            if (mediaPlayer == null)
                initMediaPlayer();
            if (isPause) {
                mediaPlayer.start();
                isPause = false;
            } else {
                // 重置mediaPlayer
                mediaPlayer.reset();
                // 重新加载音频资源
                mediaPlayer.setDataSource(mMusicUrl);
                // 准备播放（异步）
                mediaPlayer.prepareAsync();
                recordPlayProgress();
            }
            // Request audio focus for playback
            int result = mAudioManager.requestAudioFocus(afChangeListener,
                    // Use the music stream.
                    AudioManager.STREAM_MUSIC, // Request permanent focus.
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED && !isPause) {
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 暂停
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            isPause = true;
            abandonAudioFocus();
        }
    }

    // 停止
    public void stop() {
//        mediaPlayer.stop();
        if (mediaPlayer != null && mediaPlayer.isPlaying())
            mediaPlayer.reset();
    }

    private void destroy() {
        if (mRecordProgressThread != null && !mRecordProgressThread.isInterrupted()) {
            mRecordProgressThread.interrupt();
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        abandonAudioFocus();
        if (wifiLock != null && wifiLock.isHeld())
            wifiLock.release();
    }

    public void recordPlayProgress() {
        mRecordProgressThread = new Thread(() -> {
            try {
                while (!isPause) {
                    if (mediaPlayer != null) {
                        Thread.sleep(1000);
                        float percent = mediaPlayer.getCurrentPosition() * 1.0f / mediaPlayer.getDuration();
                        //logD("recordPlayProgress: " + percent);
                        if (percent <= 1)
                            mHandler.obtainMessage(MSG_CODE, percent).sendToTarget();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mRecordProgressThread.start();
    }

    /**
     * 失去焦点
     */
    private void abandonAudioFocus() {
        if (mAudioManager != null)
            mAudioManager.abandonAudioFocus(afChangeListener);
    }

    public interface IPlayProgressListener {

        void onPrepared(MediaPlayer mediaPlayer);

        void onProgress(float progress);

        void onComplete(MediaPlayer mediaPlayer);
    }

    // 定义Binder类-当然也可以写成外部类
    private MediaPlayerBinder mediaPlayerBinder = new MediaPlayerBinder();

    public class MediaPlayerBinder extends Binder {
        public Service getService() {
            return MediaPlayerBinderService.this;
        }
    }

    /**
     * 音频焦点监听
     */
    AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                if (mediaPlayer == null) {
                    initMediaPlayer();
                } else if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                // Resume playback
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                abandonAudioFocus();
                // Stop playback
            } else if (focusChange == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        }
    };

    private void logD(String msg) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, msg);
    }
}
