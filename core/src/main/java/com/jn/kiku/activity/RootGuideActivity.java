package com.jn.kiku.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.jn.kiku.R;
import com.jn.kiku.adapter.BasePagerAdapter;
import com.jn.kiku.common.api.IGuideView;
import com.jn.kiku.entiy.GuidePageVO;
import com.jn.common.SPManage;

/**
 * Author：Stevie.Chen Time：2019/8/20
 * Class Comment：根引导页界面
 */
public abstract class RootGuideActivity extends RootActivity implements IGuideView {

    protected ViewPager mViewPager = null;
    protected GuidePagerAdapter mAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_guide_layout);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        mViewPager = findViewById(R.id.vp_RootGuide);
        mAdapter = getAdapter();
        int[] imgResourceIds = getImgResourceIds();
        if (imgResourceIds != null) {
            for (int i = 0; i < imgResourceIds.length; i++) {
                mAdapter.add(new GuidePageVO()
                        .setPosition(i)
                        .setImaRes(imgResourceIds[i])
                        .setImgType(1)
                        .setLast(i == imgResourceIds.length - 1));
            }
        }
        mViewPager.setAdapter(mAdapter);
    }

    protected GuidePagerAdapter getAdapter() {
        return new GuidePagerAdapter(mActivity);
    }

    protected void handlerSkipEvent() {
        openMainActivity();
        SPManage.getInstance().setFirstGuide(false);
        finish();
    }

    public class GuidePagerAdapter extends BasePagerAdapter<GuidePageVO> {

        GuidePagerAdapter(Activity activity) {
            super(activity);
        }

        @Override
        protected int getLayoutResourceId() {
            return R.layout.common_guideitem_layout;
        }

        @Override
        public void getView(View view, int position, final GuidePageVO bean) {
            ImageView iv = view.findViewById(R.id.iv_rootGuide);
            if (bean.getImgType() == 0) {
                displayImage(iv, bean.getImgUrl());
            } else {
                iv.setImageResource(bean.getImaRes());
            }
            iv.setOnClickListener(view1 -> {
                if (bean.isLast()) {
                    handlerSkipEvent();
                }
            });
        }
    }
}
