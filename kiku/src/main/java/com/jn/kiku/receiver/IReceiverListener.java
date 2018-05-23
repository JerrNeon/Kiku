package com.jn.kiku.receiver;

import android.content.Context;
import android.content.Intent;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (广播回调)
 * @create by: chenwei
 * @date 2018/5/21 16:46
 */
public interface IReceiverListener {

    void onReceive(Context context, Intent intent);
}
