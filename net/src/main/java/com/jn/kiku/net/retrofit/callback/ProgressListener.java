package com.jn.kiku.net.retrofit.callback;

/**
 * Author：Stevie.Chen Time：2019/8/6
 * Class Comment：进度监听
 */
public interface ProgressListener {

    /**
     * @param progressBytes        已经下载或上传字节数
     * @param totalBytes           总字节数
     * @param progressPercent 进度值
     * @param done            是否完成
     */
    void onProgress(long progressBytes, long totalBytes, float progressPercent, boolean done);
}
