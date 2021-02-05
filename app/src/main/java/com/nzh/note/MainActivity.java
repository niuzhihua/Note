package com.nzh.note;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.jvm.internal.Reflection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.nzh.note.ipc.aidl.IBookManager;
import com.nzh.note.kotlin.base.ObjectKt;
import com.nzh.note.launchmode.StandardActivity;
import com.nzh.note.kotlin.base.Config;
import com.nzh.note.kotlin.myContinueation.sample.AndroidSample.CoroutineActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;


public class MainActivity extends AppCompatActivity {

    Config config;//= new Config(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        config = new Config(this);

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
            List<String> books = iBookManager.getBooks();
            Toast.makeText(MainActivity.this, books.toString(), Toast.LENGTH_SHORT).show();
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
}
