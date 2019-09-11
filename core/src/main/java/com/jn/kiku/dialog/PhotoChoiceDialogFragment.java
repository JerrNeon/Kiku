package com.jn.kiku.dialog;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;

import com.jn.kiku.R;

/**
 * Author：Stevie.Chen Time：2019/4/18
 * Class Comment：
 */
public class PhotoChoiceDialogFragment extends RootDialogFragment {

    private View.OnClickListener mTakePhotoOnClickListener;
    private View.OnClickListener mAlbumOnClickListener;

    public static PhotoChoiceDialogFragment newInstance() {
        return new PhotoChoiceDialogFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_photochoicedialog;
    }

    @Override
    protected int getAnimationStyle() {
        return R.style.bottom_in_out;
    }

    @Override
    protected boolean getCanceledOnTouchOutsideEnable() {
        return false;
    }

    @Override
    protected WindowManager.LayoutParams getLayoutParams() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        mView.findViewById(R.id.tv_takePhoto).setOnClickListener(this);
        mView.findViewById(R.id.tv_album).setOnClickListener(this);
        mView.findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    public void show(FragmentManager manager, View.OnClickListener takePhotoOnClickListener, View.OnClickListener albumOnClickListener) {
        mTakePhotoOnClickListener = takePhotoOnClickListener;
        mAlbumOnClickListener = albumOnClickListener;
        super.show(manager, getClass().getSimpleName());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int i = view.getId();
        if (i == R.id.tv_takePhoto) {
            if (mTakePhotoOnClickListener != null) {
                mTakePhotoOnClickListener.onClick(view);
            }
        } else if (i == R.id.tv_album) {
            if (mAlbumOnClickListener != null) {
                mAlbumOnClickListener.onClick(view);
            }
        } else if (i == R.id.tv_cancel) {

        }
        dismissAllowingStateLoss();
    }
}
