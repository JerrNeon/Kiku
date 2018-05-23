package com.jn.example.adapter;

import android.app.Activity;

import com.jn.example.R;
import com.jn.example.entiy.NewsVO;
import com.jn.kiku.adapter.BaseAdapterViewHolder;
import com.jn.kiku.adapter.BaseRvAdapter;

/**
 * @version V2.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2018/5/14 15:12
 */
public class NewsAdapter extends BaseRvAdapter<NewsVO> {

    public NewsAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_news;
    }

    @Override
    protected void convert(BaseAdapterViewHolder helper, NewsVO item) {
        helper
                .displayImage(mImageContext, R.id.iv_news, item.getImage())
                .setText(R.id.tv_news, item.getTitle());
    }
}
