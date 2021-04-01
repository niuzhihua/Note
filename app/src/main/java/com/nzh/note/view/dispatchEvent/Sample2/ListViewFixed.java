package com.nzh.note.view.dispatchEvent.Sample2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

class ListViewFixed extends ListView {
    public ListViewFixed(Context context) {
        super(context);
    }

    public ListViewFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int lastX;
    int lastY;


    // TODO( dispatchTouchEvent )
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int newX = (int) ev.getX();
        int newY = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 通知父View不要拦截
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:

                // 横向位移 偏移大小
                int offsetX = newX - lastX;
                // 纵向位移 偏移大小
                int offsetY = newY - lastY;

                // true : 横向滑动 ，通知父View拦截,父View消费事件。
                // false :竖直滑动，通知父View不要拦截，本child 消费事件
                boolean isParentNeed = Math.abs(offsetX) > Math.abs(offsetY);
                if (isParentNeed) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        lastX = newX;
        lastY = newY;

        return super.dispatchTouchEvent(ev);  // TODO( return super.dispatchTouchEvent )
    }
}
