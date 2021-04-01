package com.nzh.note.optimize.systools;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrength;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

public class NetTool {


    public static void wifiSignalStrength(Context applicationContext) {
        WifiManager wifiManager = (WifiManager) applicationContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int wifiState = wifiManager.getWifiState();
        WifiInfo mWifiInfo = wifiManager.getConnectionInfo();
        boolean isWifiEnable = wifiManager.isWifiEnabled();
        // 100 : 自定义的信号强度范围
        int wifiLevel = WifiManager.calculateSignalLevel(mWifiInfo.getRssi(), 1000);
        Toast.makeText(applicationContext, "wifi: 是否链接：" + isWifiEnable + "wifi状态：" + wifiState + ",信号强度：" + wifiLevel, Toast.LENGTH_SHORT).show();

    }


}
