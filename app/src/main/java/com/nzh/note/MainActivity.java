package com.nzh.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import okhttp3.internal.cache.CacheInterceptor;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.nzh.note.ipc.aidl.IBookManager;
import com.nzh.note.launchmode.StandardActivity;
import com.nzh.note.kotlin.base.Config;
import com.nzh.note.kotlin.myContinueation.sample.AndroidSample.CoroutineActivity;
import com.nzh.note.optimize.SpTestActivity;
import com.nzh.note.optimize.ViewCache.CreateViewThread;
import com.nzh.note.optimize.ViewCache.TestActivity;
import com.nzh.note.optimize.systools.NetTool;
import com.nzh.note.view.dispatchEvent.Sample2.Demo2Activity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Config config;//= new Config(this);

    Button btn_BindService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Trace.beginSection("MainActivity_onCreate");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        config = new Config(this);
        setContentView(R.layout.activity_main);


        // 5秒后界面才响应触摸事件
        btn_BindService = findViewById(R.id.btn_BindService);
        btn_BindService.postDelayed(new Runnable() {
            @Override
            public void run() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }, 1000);


        Trace.endSection();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void propertySet(View view) {
        config.setName(String.valueOf(System.currentTimeMillis()));
        config.setAge(28);
        config.setLongProperty(System.currentTimeMillis());
        config.setXX(true);
        config.setMoney(180.88f);
        config.setFirstName("first name");
        Toast.makeText(this, "Set:" + config, Toast.LENGTH_SHORT).show();

        ArrayList<String> s = new ArrayList<>(10);
    }

    public void propertyGet(View view) {

        Toast.makeText(this, "Get:" + config, Toast.LENGTH_SHORT).show();
    }

    public void callback2coroutine(View view) {

        startActivity(new Intent(this, CoroutineActivity.class));
    }

    public void testLaunchmode(View view) {
        startActivity(new Intent(this, StandardActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity is destroyed");


    }

    //-----------------------ipc start----------------------
    IBookManager iBookManager;

    public void bindService(View view) {
        Toast.makeText(this, "id:" + android.os.Process.myPid() +
                "\r\napplication:" + this.getApplication(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
//        intent.setAction("com.nzh.note.ipc.MyService");
        intent.setPackage("com.nzh.note");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public void unBindService(View view) {
        unbindService(null);
    }

    public void add(View view) {
        try {
            iBookManager.addBook("Book:" + System.currentTimeMillis());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void get(View view) {
        try {
            long start = System.currentTimeMillis();

            // IPC 耗时 ，如果是有返回值的ipc调用，那么就是同步的调用。client端会挂起等待 server服务返回值。
            List<String> books = iBookManager.getBooks();
            long t = System.currentTimeMillis() - start;
            Toast.makeText(MainActivity.this, "耗时：" + t + "\r\n" + books.toString(), Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "onServiceConnected", Toast.LENGTH_SHORT).show();


            // asInterface多了一层拦截，利用service 去本地查找 iBookManager。
            // 若不加拦截，这样好像也可以 new IBookManager.Proxy(service);
            iBookManager = IBookManager.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "onServiceDisconnected", Toast.LENGTH_SHORT).show();
        }
    };
    //-----------------------ipc end-----------------


    public void testEventDispatch(View view) {
//        startActivity(new Intent(this, com.nzh.note.view.dispatchEvent.Sample1.DemoActivity.class));
//        Toast.makeText(this, "pid:" + pid, Toast.LENGTH_SHORT).show();
    }

    public void testEventDispatch2(View view) {
        startActivity(new Intent(this, Demo2Activity.class));
    }

    public void toSpActivity(View view) {
//        boolean b1 = getApplicationContext() == getApplication();
//        boolean b2 = getApplicationContext() instanceof Application;
//        Toast.makeText(this, "b1=" + b1 + ",b2=" + b2, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "view.getContext:" + (view.getContext() == this), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "id:" + android.os.Process.myPid(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, SpTestActivity.class));
//        Toast.makeText(this, "getBaseContext():" + getBaseContext(), Toast.LENGTH_SHORT).show();

    }

    public void createView(View view) {
        new CreateViewThread(this.getApplicationContext(), R.layout.activity_sp_test).start();
    }

    public void toTestActivity(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }

    public void testWifi(View view) {
        NetTool.wifiSignalStrength(this.getApplicationContext());
    }

    public void testNet(View view) {

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Trace.endSection();
    }


}
