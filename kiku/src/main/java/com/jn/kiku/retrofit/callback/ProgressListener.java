package com.jn.kiku.retrofit.callback;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (进度监听)
 * @create by: chenwei
 * @date 2018/5/21 15:37
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
