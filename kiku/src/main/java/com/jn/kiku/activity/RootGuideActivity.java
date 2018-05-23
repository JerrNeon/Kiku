package com.jn.kiku.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.jn.kiku.R;
import com.jn.kiku.adapter.BasePagerAdapter;
import com.jn.kiku.common.api.IGuideView;
import com.jn.kiku.entiy.GuidePageVO;
import com.jn.kiku.utils.manager.SPManage;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (根引导页界面)
 * @create by: chenwei
 * @date 2018/5/17 16:43
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
        mAdapter = new GuidePagerAdapter(mActivity);
        int[] imgResourceIds = getImgResourceIds();
        if (imgResourceIds != null) {
            for (int i = 0; i < imgResourceIds.length; i++) {
                mAdapter.add(new GuidePageVO()
                        .setImaRes(imgResourceIds[i])
                        .setImgType(1)
                        .setLast(i == imgResourceIds.length - 1));
            }
        }
        mViewPager.setAdapter(mAdapter);
    }

    class GuidePagerAdapter extends BasePagerAdapter<GuidePageVO> {

        public GuidePagerAdapter(Activity activity) {
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
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.isLast()) {
                        openMainActivity();
                        SPManage.getInstance(mContext).setFirstGuide(false);
                        finish();
                    }
                }
            });
        }
    }
}
