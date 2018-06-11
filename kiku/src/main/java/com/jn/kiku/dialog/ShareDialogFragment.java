package com.jn.kiku.dialog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (分享对话框)
 * @create by: chenwei
 * @date 2018/5/30 15:18
 */
public class ShareDialogFragment extends RootDialogFragment implements BaseQuickAdapter.OnItemClickListener {

    protected RecyclerView mRecyclerView;
    protected TextView mTvCancel;

    protected onItemClickListener mOnItemClickListener = null;
    protected OnShareResultListener mOnShareResultListener = null;//分享回调-个别情况特殊处理(微信登录、分享、支付都必须安装客户端)

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
        params.width = (int) (ScreenUtils.getScreenWidth(mContext) * 0.94);//宽度为全屏
        return params;
    }

    @Override
    public void initView() {
        mRecyclerView = mView.findViewById(R.id.rv_share);
        mTvCancel = mView.findViewById(R.id.tv_shareCancel);
        mTvCancel.setOnClickListener(this);

        ShareAdapter adapter = new ShareAdapter(mFragment);
        adapter.addData(new ShareVO(R.drawable.ic_kiku_wxcircle_logo, "朋友圈"));
        adapter.addData(new ShareVO(R.drawable.ic_kiku_wechat_logo, "微信"));
        adapter.addData(new ShareVO(R.drawable.ic_kiku_sina_logo, "微博"));
        adapter.addData(new ShareVO(R.drawable.ic_kiku_qq_logo, "QQ"));
        //adapter.add(new ShareVO(R.drawable.ic_qq_logo, "QQ空间"));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
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
     * @param listener              监听器
     * @param onShareResultListener 分享回调
     */
    public void show(FragmentManager manager, String tag, onItemClickListener listener, OnShareResultListener onShareResultListener) {
        this.show(manager, tag);
        mOnItemClickListener = listener;
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
                shareWithWeChat();
                break;
            case 1:
                shareWithWeChatCircle();
                break;
            case 2:
                shareWithSina();
                break;
            case 3:
                shareWithQq();
                break;
            default:
                break;
        }
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(position);
        this.dismiss();
    }

    protected void shareWithWeChat() {
    }

    protected void shareWithWeChatCircle() {
    }

    protected void shareWithSina() {
    }

    protected void shareWithQq() {

    }

    private class ShareAdapter extends BaseRvAdapter<ShareVO> {

        public ShareAdapter(Fragment fragment) {
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

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public interface OnShareResultListener {
        void onFailure();
    }
}
