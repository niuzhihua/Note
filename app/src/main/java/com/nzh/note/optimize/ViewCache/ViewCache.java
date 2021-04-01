package com.nzh.note.optimize.ViewCache;

import android.view.View;

import java.util.concurrent.ConcurrentHashMap;

public class ViewCache {

    public static ConcurrentHashMap<Integer, View> map = new ConcurrentHashMap<>();

}
