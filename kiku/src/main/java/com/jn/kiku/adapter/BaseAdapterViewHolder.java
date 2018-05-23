package com.jn.kiku.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jn.kiku.common.api.IImageAdapterView;
import com.jn.kiku.utils.manager.GlideManage;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (BaseAdapterViewHolder)
 * @create by: chenwei
 * @date 2018/5/14 14:41
 */
public class BaseAdapterViewHolder extends BaseViewHolder implements IImageAdapterView {

    public BaseAdapterViewHolder(View view) {
        super(view);
    }

    public BaseViewHolder setVisible(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    @Override
    public BaseAdapterViewHolder displayImage(Object context, int viewId, Object url) {
        ImageView view = getView(viewId);
        GlideManage.displayImage(context, url, view);
        return this;
    }

    @Override
    public BaseAdapterViewHolder displayRoundImage(Object context, int viewId, Object url) {
        ImageView view = getView(viewId);
        GlideManage.displayImage(context, url, view, true, false);
        return this;
    }

    @Override
    public BaseAdapterViewHolder displayAvatar(Object context, int viewId, Object url) {
        ImageView view = getView(viewId);
        GlideManage.displayImage(context, url, view, false, true);
        return this;
    }

    @Override
    public BaseAdapterViewHolder displayRoundAvatar(Object context, int viewId, Object url) {
        ImageView view = getView(viewId);
        GlideManage.displayImage(context, url, view, true, true);
        return this;
    }
}
