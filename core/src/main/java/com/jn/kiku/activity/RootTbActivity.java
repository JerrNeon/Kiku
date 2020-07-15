package com.jn.kiku.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.jn.kiku.R;
import com.jn.kiku.mvp.IBPresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author：Stevie.Chen Time：2019/8/8
 * Class Comment：带标题栏的Activity
 */
public abstract class RootTbActivity<P extends IBPresenter> extends RootActivity<P> implements View.OnClickListener {

    protected static final int ROOT_LAYOUT = 1;//root layout
    protected static final int IV_LEFT = 2;//left icon
    protected static final int TV_TITLE = 3;//center title
    protected static final int IV_RIGHT = 4;//right icon
    protected static final int TV_RIGHT = 5;//right text
    protected static final int VIEW_DIVIDER = 6;//divider
    protected static final int RESOURCE_TEXT = 1;//text resource
    protected static final int RESOURCE_COLOR = 2;//text color resource or background color resource
    protected static final int RESOURCE_DRAWABLE = 3;//drawable recource
    protected static final int RESOURCE_ENABLE = 4;//enable
    protected static final int RESOURCE_VISIBLE = 5;//visible

    protected LinearLayout mLlTitleBar;//root layout
    protected ViewStub mVsTitleBar;//titleBar ViewStub
    protected RelativeLayout mRlTitleBar;//titleBar layout
    protected ImageView mIvTitleBarLeft;//left icon
    protected TextView mTvTitleBarTitle;//center title
    protected ImageView mIvTitleBarRight;//right icon
    protected TextView mTvTitleBarRight;//right text
    protected View mViewTitleBarDivider;//divider

    @IntDef({ROOT_LAYOUT, IV_LEFT, TV_TITLE, IV_RIGHT, TV_RIGHT, VIEW_DIVIDER})
    @Retention(RetentionPolicy.SOURCE)
    @interface TitleBarType {
    }

    @IntDef({RESOURCE_TEXT, RESOURCE_COLOR, RESOURCE_DRAWABLE, RESOURCE_ENABLE, RESOURCE_VISIBLE})
    @Retention(RetentionPolicy.SOURCE)
    @interface ResourceType {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_titlebar_layout);
        initRootTbParentView();
        initRootTbView();
        setRootContainerView();
        initButterKnife();
        setStatusBar();
    }

    /**
     * get content layoutRes Id
     */
    public abstract @LayoutRes
    int getLayoutResourceId();

    /**
     * init titleBar Parent View
     */
    protected void initRootTbParentView() {
        if (getLayoutResourceId() != 0) {
            mLlTitleBar = findViewById(R.id.ll_commonTitleBar);
            mVsTitleBar = findViewById(R.id.vs_commonTitleBar);
            mViewTitleBarDivider = findViewById(R.id.view_commonTitleBarDivider);
        }
    }

    /**
     * init titleBar View
     */
    protected void initRootTbView() {
        if (getLayoutResourceId() != 0) {
            mVsTitleBar.inflate();
            mRlTitleBar = findViewById(R.id.rl_commonTitleBar);
            mIvTitleBarLeft = findViewById(R.id.iv_commonTitleBar_left);
            mTvTitleBarTitle = findViewById(R.id.tv_commonTitleBar_Title);
            mIvTitleBarRight = findViewById(R.id.iv_commonTitleBar_right);
            mTvTitleBarRight = findViewById(R.id.tv_commonTitleBar_right);
            mIvTitleBarLeft.setOnClickListener(this);
            mTvTitleBarTitle.setOnClickListener(this);
            mIvTitleBarRight.setOnClickListener(this);
            mTvTitleBarRight.setOnClickListener(this);
        }
    }

    /**
     * set main content View
     */
    protected void setRootContainerView() {
        if (getLayoutResourceId() != 0) {
            View contentView = LayoutInflater.from(mActivity).inflate(getLayoutResourceId(), null, false);
            Drawable drawable = contentView.getBackground();
            if (drawable == null) {
                contentView.setBackgroundResource(R.color.white);
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            mLlTitleBar.addView(contentView, lp);
        }
    }

    /**
     * set titleBar View style
     *
     * @param titleBarType TitleBarType
     * @param resourceType ResourceType
     * @param content      content
     */
    protected void setTitleBarView(@TitleBarType int titleBarType, @ResourceType int resourceType, Object content) {
        switch (titleBarType) {
            case ROOT_LAYOUT://root layout
                if (resourceType == RESOURCE_COLOR && content instanceof Integer) {
                    mRlTitleBar.setBackgroundResource((Integer) content);
                } else {
                    throw new IllegalArgumentException("not support type");
                }
                break;
            case IV_LEFT://left icon
                if (resourceType == RESOURCE_DRAWABLE && content instanceof Integer) {
                    mIvTitleBarLeft.setImageResource((Integer) content);
                    mIvTitleBarLeft.setVisibility(View.VISIBLE);
                } else if (resourceType == RESOURCE_ENABLE && content instanceof Boolean) {
                    mIvTitleBarLeft.setEnabled((Boolean) content);
                } else if (resourceType == RESOURCE_VISIBLE && content instanceof Integer) {
                    mIvTitleBarLeft.setVisibility((Integer) content);
                } else {
                    throw new IllegalArgumentException("not support type");
                }
                break;
            case TV_TITLE://center title
                if (resourceType == RESOURCE_TEXT) {
                    if (content instanceof Integer) {
                        mTvTitleBarTitle.setText(getResources().getString((Integer) content));
                    } else if (content instanceof String) {
                        mTvTitleBarTitle.setText((String) content);
                    } else if (content instanceof CharSequence) {
                        mTvTitleBarTitle.setText((CharSequence) content);
                    }
                    mTvTitleBarTitle.setVisibility(View.VISIBLE);
                } else if (resourceType == RESOURCE_COLOR) {
                    if (content instanceof Integer) {
                        mTvTitleBarTitle.setTextColor((Integer) content);
                    }
                    mTvTitleBarTitle.setVisibility(View.VISIBLE);
                } else if (resourceType == RESOURCE_ENABLE && content instanceof Boolean) {
                    mTvTitleBarTitle.setEnabled((Boolean) content);
                } else if (resourceType == RESOURCE_VISIBLE && content instanceof Integer) {
                    mTvTitleBarTitle.setVisibility((Integer) content);
                } else {
                    throw new IllegalArgumentException("not support type");
                }
                break;
            case IV_RIGHT://right icon
                if (resourceType == RESOURCE_DRAWABLE && content instanceof Integer) {
                    mIvTitleBarRight.setImageResource((Integer) content);
                    mIvTitleBarRight.setVisibility(View.VISIBLE);
                } else if (resourceType == RESOURCE_ENABLE && content instanceof Boolean) {
                    mIvTitleBarRight.setEnabled((Boolean) content);
                } else if (resourceType == RESOURCE_VISIBLE && content instanceof Integer) {
                    mIvTitleBarRight.setVisibility((Integer) content);
                } else {
                    throw new IllegalArgumentException("not support type");
                }
                break;
            case TV_RIGHT://right text
                if (resourceType == RESOURCE_TEXT) {
                    if (content instanceof Integer) {
                        mTvTitleBarRight.setText(getResources().getString((Integer) content));
                    } else if (content instanceof String) {
                        mTvTitleBarRight.setText((String) content);
                    } else if (content instanceof CharSequence) {
                        mTvTitleBarRight.setText((CharSequence) content);
                    }
                    mTvTitleBarRight.setVisibility(View.VISIBLE);
                } else if (resourceType == RESOURCE_COLOR) {
                    if (content instanceof Integer) {
                        mTvTitleBarRight.setTextColor((Integer) content);
                    }
                    mTvTitleBarRight.setVisibility(View.VISIBLE);
                } else if (resourceType == RESOURCE_ENABLE && content instanceof Boolean) {
                    mTvTitleBarRight.setEnabled((Boolean) content);
                } else if (resourceType == RESOURCE_VISIBLE && content instanceof Integer) {
                    mTvTitleBarRight.setVisibility((Integer) content);
                } else {
                    throw new IllegalArgumentException("not support type");
                }
                break;
            case VIEW_DIVIDER://divider
                if (resourceType == RESOURCE_COLOR && content instanceof Integer) {
                    mViewTitleBarDivider.setBackgroundResource((Integer) content);
                    mViewTitleBarDivider.setVisibility(View.VISIBLE);
                } else if (resourceType == RESOURCE_VISIBLE && content instanceof Integer) {
                    mViewTitleBarDivider.setVisibility((Integer) content);
                } else {
                    throw new IllegalArgumentException("not support type");
                }
                break;
            default:
                break;
        }
    }

    /**
     * set title
     *
     * @param titleText title content
     */
    protected void setTitleText(String titleText) {
        setTitleText(titleText, true);
    }

    /**
     * set title
     *
     * @param titleText     title content
     * @param isShowDivider is or not show divider
     */
    protected void setTitleText(String titleText, boolean isShowDivider) {
        setTitleBarView(TV_TITLE, RESOURCE_TEXT, titleText);
        setTitleBarView(VIEW_DIVIDER, RESOURCE_VISIBLE, isShowDivider ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.iv_commonTitleBar_left) {
            onClickTitleBar(IV_LEFT);
            finish();
        } else if (viewId == R.id.tv_commonTitleBar_Title) {
            onClickTitleBar(TV_TITLE);
        } else if (viewId == R.id.iv_commonTitleBar_right) {
            onClickTitleBar(IV_RIGHT);
        } else if (viewId == R.id.tv_commonTitleBar_right) {
            onClickTitleBar(TV_RIGHT);
        }
    }

    /**
     * onClick titleBar view
     *
     * @param titleBarType TitleBarType
     */
    protected void onClickTitleBar(@TitleBarType int titleBarType) {

    }
}
