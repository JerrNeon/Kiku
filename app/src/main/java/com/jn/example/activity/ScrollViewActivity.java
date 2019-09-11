package com.jn.example.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ScrollView;

import com.jn.example.R;
import com.jn.kiku.activity.RootTbActivity;
import com.jn.kiku.utils.ImageUtil;

import butterknife.BindView;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (生成图片的Scrollview)
 * @create by: chenwei
 * @date 2018/5/22 16:38
 */
public class ScrollViewActivity extends RootTbActivity {

    @BindView(R.id.sv)
    ScrollView mScrollView;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_scrollview;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleBarView(TV_RIGHT, RESOURCE_TEXT, "生成图片");
    }

    @Override
    protected void onClickTitleBar(int titleBarType) {
        super.onClickTitleBar(titleBarType);
        if (titleBarType == TV_RIGHT) {
            //Scrollview中的内容生成一张图片
            String path = ImageUtil.saveBitmap(mScrollView, ContextCompat.getColor(mContext, com.jn.kiku.R.color.white), "scrollview");
            logI(path);
        }
    }
}
