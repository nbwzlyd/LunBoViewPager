package com.example.viewpagerlibrary.view.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;


/**
 * Created by liyaodong on 2018/3/22.
 * <p>
 * 轮播viewpager
 */

public class BaseCarouselFigureViewPager extends ViewPager {

    protected static float MIN_SCALE = 0.8f;
    protected static long TIME = 3500L;
    private Handler mHandler;
    private volatile boolean mCanLunBo = false;

    public BaseCarouselFigureViewPager(Context context) {
        this(context, null);
    }

    public BaseCarouselFigureViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setCanLunBo(boolean canLunBo) {
        mCanLunBo = canLunBo;
    }

    protected void goNextPage(){
        setCurrentItem(getCurrentItem()+1);
    }

    private void init() {
        mHandler = new Handler(Looper.myLooper());
        setOffscreenPageLimit(7);
        setLunBoTime(1000);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        pause();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        startLunBo();
                }
                return false;
            }
        });
    }

    private void setLunBoTime(int time) {//反射设置轮播时间间隔
        try {
            // 通过class文件获取mScroller属性
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(getContext());
            scroller.setmDuration(time);
            mField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class ScaleTransFormer implements ViewPager.PageTransformer {//缩放

        public float getTranslateY(int height, float scaleY) {//设置y方向偏移
            return -height * (1 - scaleY) / 4;
        }

        @Override
        public void transformPage(View page, float position) {
            //position 最左为-1 中间为0 最右为1

            int height = page.getMeasuredHeight();
            if (position < -1 || position > 1) {
                page.setScaleY(MIN_SCALE);
                page.setTranslationY(getTranslateY(height, MIN_SCALE));
            }

            if (position <= 1) {
                float scaleY;
                if (position >= 0) {
                    scaleY = 1 - (1 - MIN_SCALE) * position;
                } else {
                    scaleY = 1 + (1f - MIN_SCALE) * position;
                }
                page.setScaleY(scaleY);
                page.setTranslationY(getTranslateY(height, scaleY));
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pause();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Field mFirstLayout = null;
        try {
            mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
            mFirstLayout.setAccessible(true);
            mFirstLayout.set(this, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        startLunBo();
    }

    private class LunBoRunnable implements Runnable {

        @Override
        public void run() {
            goNextPage();
            mHandler.postDelayed(this, TIME);
        }
    }

    public void startLunBo() {
        if (mCanLunBo) {
            pause();
            mHandler.postDelayed(new LunBoRunnable(), TIME);
        }
    }

    public void pause() {
        mHandler.removeCallbacksAndMessages(null);
    }

     class FixedSpeedScroller extends Scroller {
        private int mDuration = 1000;
        public FixedSpeedScroller(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        /**
         * 设置切换时间
         */
        public void setmDuration(int time) {
            mDuration = time;
        }

        /**
         * 获取切换时间
         */
        public int getmDuration() {
            return mDuration;
        }
    }

    public void goPageIndex() {
        int pageIndex = 0;
        if (0 == getCurrentItem()) {
            pageIndex = getAdapter().getCount() - 4;
            this.setCurrentItem(pageIndex, false);
        }
        if (1 == getCurrentItem()) {
            pageIndex = getAdapter().getCount() - 3;
            this.setCurrentItem(pageIndex, false);
        }
        if (getCurrentItem() == getAdapter().getCount() - 2) {
            pageIndex = 2;
            this.setCurrentItem(pageIndex, false);
        }
        if (getCurrentItem() == getAdapter().getCount() - 1) {
            pageIndex = 3;
            this.setCurrentItem(pageIndex, false);
        }
    }
}
