package com.nzh.note.optimize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.nzh.note.R;
import com.nzh.note.optimize.ViewCache.ViewCache;
import com.nzh.note.optimize.fps.FpsUtil;

public class SpTestActivity extends AppCompatActivity {

    /**
     * sp在创建的时候会把整个文件全部加载进内存，如果你的sp文件比较大，那么会带来几个严重问题：
     * 1、第一次从sp中获取值的时候，有可能阻塞主线程，使界面卡顿、掉帧。
     * 2、解析sp的时候会产生大量的临时对象，导致频繁GC，引起界面卡顿。
     * 3、这些key和value会永远存在于内存之中，占用大量内存。 (浪费内存)
     */
    SharedPreferences sp;  // 实现类：SharedPreferencesImpl

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long startTime = SystemClock.currentThreadTimeMillis();
        View view = ViewCache.map.get(R.layout.activity_sp_test);
        if (view != null) {
            setContentView(view);
        } else {
            setContentView(R.layout.activity_sp_test);
        }
        long spendTime = SystemClock.currentThreadTimeMillis() - startTime;
        System.out.println("SpTestActivity view == null:" + (view == null) + " ,spendTime:" + spendTime);

        sp = this.getSharedPreferences("shared_pref", MODE_PRIVATE);


    }


    public void openFps(View view) {
        FpsUtil.open();
    }

    public void closeFps(View view) {
        FpsUtil.close();
    }

    public void testPut(View view) {
        String name = sp.getString("name", "default");
    }

    public void testGet(View view) {
        sp.edit().putString("name", "value").apply();
        sp.edit().putString("age", "12").apply();
        sp.edit().putString("height", "180").apply();
    }


}