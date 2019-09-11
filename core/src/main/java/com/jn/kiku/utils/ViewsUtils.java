package com.jn.kiku.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.jn.common.util.DensityUtils;
import com.jn.common.util.LogUtils;

import java.lang.reflect.Field;

/**
 * Author：Stevie.Chen Time：2019/8/13
 * Class Comment：View工具
 */
public class ViewsUtils {

    /**
     * 判断RecyclerView最后一个child是否完全显示出来
     *
     * @return true完全显示出来，否则false
     */
    public static boolean isLastItemVisible(RecyclerView recyclerView) {
        final RecyclerView.Adapter adapter = recyclerView.getAdapter();

        if (null == adapter) {
            return true;
        }
        int firstVisiblePosition = 0;
        int lastVisiblePosition = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            firstVisiblePosition = gridLayoutManager.findFirstVisibleItemPosition();
            lastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
            lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] firstVisiblePositions = staggeredGridLayoutManager.findFirstVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);
            int[] lastVisiblePositions = staggeredGridLayoutManager.findLastVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);
            firstVisiblePosition = firstVisiblePositions[0];
            lastVisiblePosition = lastVisiblePositions[0];
            for (int firstPosition : firstVisiblePositions) {
                if (firstPosition < firstVisiblePosition)
                    firstVisiblePosition = firstPosition;
            }
            for (int lastPosition : lastVisiblePositions) {
                if (lastPosition > lastVisiblePosition)
                    lastVisiblePosition = lastPosition;
            }
        }
        final int lastItemPosition = adapter.getItemCount() - 1;

        /*
         * This check should really just be: lastVisiblePosition == lastItemPosition, but ListView
         * internally uses a FooterView which messes the positions up. For me we'll just subtract
         * one to account for it and rely on the inner condition which checks getBottom().
         */
        if (lastVisiblePosition >= lastItemPosition - 1) {
            final int childIndex = lastVisiblePosition - firstVisiblePosition;
            final int childCount = adapter.getItemCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = recyclerView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= recyclerView.getBottom();
            }
        }

        return false;
    }

    /**
     * 判断RecyclerView最后一个child是否滑动到底部
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange();
    }

    /**
     * 判断RecyclerView最后一个child是否滑动到底部
     */
    public static boolean isSlideBottom(RecyclerView recyclerView) {
        return !recyclerView.canScrollVertically(1);
    }

    /**
     * 判断RecyclerView最后一个child是否滑动到顶部
     */
    public static boolean isSlideTop(RecyclerView recyclerView) {
        return !recyclerView.canScrollVertically(-1);
    }

    /**
     * 判断ListView最后一个child是否完全显示出来
     *
     * @return true完全显示出来，否则false
     */
    public static boolean isLastItemVisible(AbsListView listView) {
        final Adapter adapter = listView.getAdapter();

        if (null == adapter || adapter.isEmpty()) {
            return true;
        }

        final int lastItemPosition = adapter.getCount() - 1;
        final int lastVisiblePosition = listView.getLastVisiblePosition();

        /*
         * This check should really just be: lastVisiblePosition == lastItemPosition, but ListView
         * internally uses a FooterView which messes the positions up. For me we'll just subtract
         * one to account for it and rely on the inner condition which checks getBottom().
         */
        if (lastVisiblePosition >= lastItemPosition - 1) {
            final int childIndex = lastVisiblePosition - listView.getFirstVisiblePosition();
            final int childCount = listView.getChildCount();
            final int index = Math.min(childIndex, childCount - 1);
            final View lastVisibleChild = listView.getChildAt(index);
            if (lastVisibleChild != null) {
                return lastVisibleChild.getBottom() <= listView.getBottom();
            }
        }

        return false;
    }

    /**
     * 取消刷新时动画(解决RecyclerView局部刷新时闪烁)
     */
    public static void cancelItemAnimator(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            if (itemAnimator != null) {
                itemAnimator.setAddDuration(0);
                itemAnimator.setChangeDuration(0);
                itemAnimator.setMoveDuration(0);
                itemAnimator.setRemoveDuration(0);
                if (itemAnimator instanceof DefaultItemAnimator) {
                    ((DefaultItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
                }
            }
        }
    }

    /**
     * NestedScrollView是否滑动到底部
     */
    public static boolean isSlideToBottom(NestedScrollView v, int scrollY) {
        return (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()));
    }

    /**
     * 获取View的宽高
     * <p>
     * onCreate() 中 View.getWidth 和 View.getHeight 无法获得一个 view 的高度和宽度，
     * 这是因为 View 组件布局要在 onResume() 回调后完成。
     * 所以现在需要使用 getViewTreeObserver().addOnGlobalLayoutListener() 来获得宽度或者高度。
     * 这是获得一个 view 的宽度和高度的方法之一。
     * 但是需要注意的是 OnGlobalLayoutListener 可能会被多次触发，因此在得到了高度之后，要将OnGlobalLayoutListener 注销掉。
     * </p>
     */
    public static void addOnGlobalLayoutListener(final View view, final ViewTreeObserver.OnGlobalLayoutListener listener) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (listener != null)
                    listener.onGlobalLayout();
                LogUtils.d("width>>> " + view.getWidth());
                LogUtils.d("height>>> " + view.getHeight());
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /**
     * 获取控件距离屏幕顶部的距离(不包含状态栏)
     */
    public static int getTopMargin(Context context, final View view) {
        int topMargin = 0;
        try {
            int[] location = new int[2];
            view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
            topMargin = location[1] - ScreenUtils.getStatusHeight(context);//Y坐标减去状态高度就是距离屏幕顶部的高度
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topMargin;
    }

    /**
     * 获取控件的宽高(设置为View.GONE状态的也可以获取到)
     */
    public static int[] getUnDisplayViewSize(View view) {
        int[] size = new int[2];
        try {
            int width = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int height = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            view.measure(width, height);
            size[0] = view.getMeasuredWidth();
            size[1] = view.getMeasuredHeight();
            return size;
        } catch (NullPointerException e) {
            e.printStackTrace();
            size[0] = 0;
        } catch (Exception e) {
            e.printStackTrace();
            size[0] = 0;
        }
        return size;
    }

    /**
     * 设置TabLayout子项左右margin值
     *
     * @param tabLayout   TabLayout
     * @param marginLeft  左margin
     * @param marginRight 右margin
     */
    public static void setTabLayoutIndicatorMargin(Context context, TabLayout tabLayout, int marginLeft, int marginRight) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            if (layout != null) {
                for (int i = 0; i < layout.getChildCount(); i++) {
                    View child = layout.getChildAt(i);
                    child.setPadding(0, 0, 0, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    params.setMarginStart(DensityUtils.dp2px(context.getApplicationContext(), marginLeft));
                    params.setMarginEnd(DensityUtils.dp2px(context.getApplicationContext(), marginRight));
                    child.setLayoutParams(params);
                    child.invalidate();
                }
            }
        } catch (NullPointerException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解决TabLayout调用了setupWithViewPager方法后导致TabLayout自定义View失效的问题
     * 如果想要实现联动效果，调用以下两个方法
     */
    public static void setupTabLayoutWithViewPager(@NonNull TabLayout tabLayout, @NonNull ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }
}
