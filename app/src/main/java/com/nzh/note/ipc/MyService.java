package com.nzh.note.ipc;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Window;

import com.nzh.note.ipc.aidl.IBookManager;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private ArrayList<String> list = new ArrayList<String>();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("----------onUnbind-------------");
        return super.onUnbind(intent);
    }

    Binder binder = new IBookManager.Stub() {
        @Override
        public void addBook(String book) throws RemoteException {
            list.add(book);
        }

        @Override
        public List<String> getBooks() throws RemoteException {
            return list;
        }
    };
}