package com.nzh.note.optimize.systools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Systool {

    public static void test() {

        //        int pid = android.os.Process.myPid();

//        exeCmd("vmstat", false);
//        exeCmd("top -n 1", false);  // https://blog.csdn.net/xingchenxuanfeng/article/details/50772203
//        exeCmd("ps");   //
//        exeCmd("free");

        // 网络
//        exeCmd("netstat");
//        exeCmd("ifconfig");
//        exeCmd("cat /proc/net/netstat");


//        exeCmd("uptime");   //
//        exeCmd("dumpsys gfxinfo 包名 ");   //
//        exeCmd("dumpsys gfxinfo 包名 framestats");   //
//        exeCmd("dumpsys gfxinfo SurfaceFlinger");   //
//        exeCmd("uptime");   //
//
        // wifi信息打印
//        net(this.getApplicationContext());
        // 内存信息
//        exeCmd("dumpsys meminfo $pid");
//        exeCmd("dumpsys meminfo --package $PackageName");
//        exeCmd("cat /proc/meminfo");

        // dumpsys命令 查询系统服务的运行状态
//        exeCmd("dumpsys -l"); // 查看当前系统所支持的dump服务

        // AMS之dumpsys篇


    }


    private void exeCmd(String cmd, boolean showCountOrContent) {
        try {

            Process p = Runtime.getRuntime().exec(cmd);
            //取得命令结果的输出流
            InputStream fis = p.getInputStream();
            //用一个读输出流类去读
            InputStreamReader isr = new InputStreamReader(fis);
            //用缓冲器读行
            BufferedReader br = new BufferedReader(isr);

            //直到读完为止
            String line = null;
            if (showCountOrContent) {
                int count = 0;
                while ((line = br.readLine()) != null) {
                    count++;
                }
                System.out.println("count:" + count + "行");
            } else {
                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\r\n");
                }
                System.out.println(sb);
                sb.delete(0, sb.length() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
