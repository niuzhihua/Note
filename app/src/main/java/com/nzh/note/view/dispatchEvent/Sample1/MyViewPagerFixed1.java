package com.nzh.note.view.dispatchEvent.Sample1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class MyViewPagerFixed1 extends ViewPager {

    public MyViewPagerFixed1(@NonNull Context context) {
        super(context);
    }

    public MyViewPagerFixed1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int lastX;
    int lastY;

    // 如果父View 需要事件就拦截，不需要就不拦截。
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int newX = (int) ev.getX();
        int newY = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                super.onInterceptTouchEvent(ev);
                // 不拦截
                // 为什么不拦截？
                // 因为如果拦截了，child就永远接收不到事件了，而child和ViewPager都需要响应事件的。
                // 一般情况不在 ACTION_DOWN 中拦截。
                isIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:

                // 横向位移 偏移大小
                int offsetX = newX - lastX;
                // 纵向位移 偏移大小
                int offsetY = newY - lastY;

                // true : 横向滑动 ，拦截事件，自己消费， 目的：ViewPager可以横向滑动。
                // false :竖直滑动，不拦截事件，交给child消费 ，目的：listView可以竖直滑动
                isIntercept = Math.abs(offsetX) > Math.abs(offsetY);

                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                break;
        }

        lastX = newX;
        lastY = newY;
        return isIntercept;
    }
}
