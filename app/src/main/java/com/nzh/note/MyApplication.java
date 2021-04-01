package com.nzh.note;

import android.app.Application;
import android.content.Context;
import android.os.Trace;
import android.view.View;

import java.lang.reflect.Method;


public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        openAppUseTrace(); // 加不加都行
        Trace.beginSection("MyApplication_attachBaseContext");

    }

    private void openAppUseTrace() {
        try {
            Class<?> trace = Class.forName("android.os.Trace");
            Method setAppTracingAllowed = trace.getDeclaredMethod("setAppTracingAllowed", boolean.class);
            setAppTracingAllowed.invoke(null, true);
            System.out.println("非debug app可以使用 label了。。。");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("--------------Exception---------------");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
