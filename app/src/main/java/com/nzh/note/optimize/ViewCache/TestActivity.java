package com.nzh.note.optimize.ViewCache;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.nzh.note.R;
import com.nzh.note.optimize.ViewCache.ViewCache;

public class TestActivity extends AppCompatActivity {

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
        System.out.println("TestActivity view == null:" + (view == null) + " ,spendTime:" + spendTime);


    }
}