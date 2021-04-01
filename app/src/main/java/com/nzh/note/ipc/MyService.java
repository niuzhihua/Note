package com.nzh.note.ipc;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.view.Window;
import android.widget.Toast;

import com.nzh.note.ipc.aidl.IBookManager;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private ArrayList<String> list = new ArrayList<String>();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("currentThread:" + Thread.currentThread().getName());
        Toast.makeText(this, "id:" + android.os.Process.myPid() +
                "\r\napplication:" + this.getApplication() +
                "\r\ncurrentThread:" + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
        return binder;
    }

    private static final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("----------onUnbind-------------");
        return super.onUnbind(intent);
    }

    Binder binder = new IBookManager.Stub() {
        // 运行在 子线程
        @Override
        public void addBook(String book) throws RemoteException {

            list.add(book);

            System.out.println("currentThread:" + Thread.currentThread().getName());

        }

        @Override
        public List<String> getBooks() throws RemoteException {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return list;
        }
    };
}