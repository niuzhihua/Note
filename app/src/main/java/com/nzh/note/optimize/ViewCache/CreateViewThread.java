package com.nzh.note.optimize.ViewCache;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 异步创建View线程
 */
public class CreateViewThread extends Thread {
    Context c;
    final Integer id;

    /**
     *
     * @param c
     * @param id layout文件id
     */
    public CreateViewThread(Context c, Integer id) {
        this.c = c;
        this.id = id;
    }

    public static MyViewAttachToWindowListener listener;

    public static class MyViewAttachToWindowListener implements View.OnAttachStateChangeListener {
        public Integer id;

        public MyViewAttachToWindowListener(Integer id) {
            this.id = id;
        }

        @Override
        public void onViewAttachedToWindow(View v) {

        }

        /**
         * 释放View Tree 属性
         * @param view
         */
        public void releaseViewProperty(View view) {
            ViewGroup self = (ViewGroup) view;
            int c = self.getChildCount();
            for (int i = 0; i < c; i++) {
                View child = self.getChildAt(i);
                if (child instanceof ViewParent) {
                    releaseViewProperty(child);
                } else {
                    if (child instanceof TextView) {
                        ((TextView) child).setText("");
                    } else if (child instanceof ImageView) {
                        // TODO
                    } else {

                    }
                }
            }

        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            View view = ViewCache.map.get(id);
            if (view == v) {
                //1、 清除脏数据 ： 可以考虑：1、遍历View Tree 2、将View的属性设为默认
                releaseViewProperty(view);
            }

            if (view != null) {
                //2、  删除 原来listener,否则removeView会触发 onViewDetachedFromWindow , 而onViewDetachedFromWindow又触发 removeView，造成递归死循环。
                view.removeOnAttachStateChangeListener(listener);

                ViewGroup parent = (ViewGroup) view.getParent();
                parent.removeView(view);

                //3、 重新设置listener来监听 view
                view.addOnAttachStateChangeListener(listener);
            }

            System.out.println("----onViewDetachedFromWindow---");
        }

    }


    @Override
    public void run() {

        if (ViewCache.map.get(id) == null && id > 0) {
            View view = LayoutInflater.from(c).inflate(id, null);
            if (view != null) {
                if (listener == null) {
                    listener = new MyViewAttachToWindowListener(id);
                }

                view.addOnAttachStateChangeListener(listener);
                ViewCache.map.put(id, view);
            }
        }
        System.out.println("map size:" + ViewCache.map.size());
    }
}