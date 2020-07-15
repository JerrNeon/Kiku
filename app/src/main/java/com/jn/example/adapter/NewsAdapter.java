package com.jn.example.adapter;

import android.app.Activity;

import com.jn.example.R;
import com.jn.example.entiy.NewsVO;
import com.jn.kiku.adapter.BaseAdapterViewHolder;
import com.jn.kiku.adapter.BaseRvAdapter;

/**
 * Author：Stevie.Chen Time：2019/9/11
 * Class Comment：
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
                .displayImage(R.id.iv_news, item.getThumbnail())
                .setText(R.id.tv_news, item.getText());
    }
}
