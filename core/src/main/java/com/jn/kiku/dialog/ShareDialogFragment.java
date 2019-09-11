package com.jn.kiku.dialog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jn.kiku.R;
import com.jn.kiku.adapter.BaseAdapterViewHolder;
import com.jn.kiku.adapter.BaseRvAdapter;
import com.jn.kiku.entiy.ShareVO;
import com.jn.kiku.utils.ScreenUtils;

/**
 * Author：Stevie.Chen Time：2019/8/2
 * Class Comment：分享对话框
 */
public class ShareDialogFragment extends RootDialogFragment implements BaseQuickAdapter.OnItemClickListener {

    protected RecyclerView mRecyclerView;
    protected TextView mTvCancel;

    protected OnShareResultListener mOnShareResultListener = null;//分享回调-个别情况特殊处理(微信登录、分享、支付都必须安装客户端)

    public static ShareDialogFragment newInstance() {
        return new ShareDialogFragment();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_share;
    }

    @Override
    protected int getAnimationStyle() {
        return R.style.bottom_in_out;
    }

    @Override
    protected boolean getCanceledOnTouchOutsideEnable() {
        return true;
    }

    @Override
    protected WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.94);//宽度为全屏
        return params;
    }

    @Override
    public void initView() {
        mRecyclerView = mView.findViewById(R.id.rv_share);
        mTvCancel = mView.findViewById(R.id.tv_shareCancel);
        mTvCancel.setOnClickListener(this);

        ShareAdapter adapter = new ShareAdapter(mFragment);
        adapter.addData(new ShareVO(R.drawable.ic_kiku_wechat_logo, "微信"));
        adapter.addData(new ShareVO(R.drawable.ic_kiku_wxcircle_logo, "朋友圈"));
        adapter.addData(new ShareVO(R.drawable.ic_kiku_sina_logo, "微博"));
        adapter.addData(new ShareVO(R.drawable.ic_kiku_qq_logo, "QQ"));
        adapter.addData(new ShareVO(R.drawable.ic_kiku_qzone_logo, "QQ空间"));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {

    }

    /**
     * 显示对话框
     *
     * @param manager               FragmentManager
     * @param tag                   tag标识
     * @param onShareResultListener 分享回调
     */
    public void show(FragmentManager manager, String tag, OnShareResultListener onShareResultListener) {
        this.show(manager, tag);
        mOnShareResultListener = onShareResultListener;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_shareCancel) {
            this.dismiss();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                if (mOnShareResultListener != null)
                    mOnShareResultListener.shareWeChat();
                break;
            case 1:
                if (mOnShareResultListener != null)
                    mOnShareResultListener.shareWeChatCircle();
                break;
            case 2:
                if (mOnShareResultListener != null)
                    mOnShareResultListener.shareSina();
                break;
            case 3:
                if (mOnShareResultListener != null)
                    mOnShareResultListener.shareQq();
                break;
            case 4:
                if (mOnShareResultListener != null)
                    mOnShareResultListener.shareQqZone();
                break;
            default:
                break;
        }
        this.dismiss();
    }

    private class ShareAdapter extends BaseRvAdapter<ShareVO> {

        ShareAdapter(Fragment fragment) {
            super(fragment);
        }

        @Override
        public int getLayoutResourceId() {
            return R.layout.dialog_item_share;
        }

        @Override
        protected void convert(BaseAdapterViewHolder helper, ShareVO item) {
            helper.setImageResource(R.id.iv_share, item.getImg());
            helper.setText(R.id.tv_share, item.getTitle());
        }
    }

    public interface OnShareResultListener {
        void shareWeChat();

        void shareWeChatCircle();

        void shareSina();

        void shareQq();

        void shareQqZone();
    }
}
