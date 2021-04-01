package com.nzh.note.view.dispatchEvent.Sample2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class MyViewPagerFixed2 extends ViewPager {

    public MyViewPagerFixed2(@NonNull Context context) {
        super(context);
    }

    public MyViewPagerFixed2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 父view 默认不拦截事件，由child决定事件的拦截，子元素不需要事件时操控父view处理。
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            // 由child 决定
            super.onInterceptTouchEvent(ev);
            // 由child 决定
            return true;
        }
        // 默认不拦截
        return false;
    }
}
