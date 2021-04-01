package com.nzh.note.optimize.fps;

import android.os.Build;
import android.util.Log;
import android.view.Choreographer;

import java.util.concurrent.TimeUnit;

public class FpsUtil {
    private static long startFrameTimeMillis = 0;  // 当前帧 的 cpu 时间
    private static int numFramesRendered = 0;     // 记录 1 s 内  渲染了多少帧

    private static final int interval = 1000;  // 这个值表示 1000 毫秒。 即1 秒。

    public static void open() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }

        Choreographer.getInstance().postFrameCallback(callback);
    }

    public static void close() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return;
        }
        if (callback != null) {
            Choreographer.getInstance().removeFrameCallback(callback);
        }

    }


    private static Choreographer.FrameCallback callback = new Choreographer.FrameCallback() {
        /**
         * 1s =  1000 (ms) = 1000 000 000  (ns)
         *  1毫秒 = 1000 * 1000 纳秒
         * 1 ms = 1000 * 1000 (ns)
         * @param frameTimeNanos  纳秒
         */
        @Override
        public void doFrame(long frameTimeNanos) {
            // 纳秒 转为 毫秒
            long currentFrameTimeMillis = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos);

            if (startFrameTimeMillis > 0) {  //
                // 渲染一帧   花费了多少 毫秒。
                long duration = currentFrameTimeMillis - startFrameTimeMillis;
                numFramesRendered++;
                // 判断是否超过 1 s ， 如果超过了1s,则计算1s内的 fps.
                if (duration > interval) {

                    // fps = 总时间 / 每帧的时间
                    double fps = numFramesRendered * 1000f / duration;

                    // 连续两次fps < 40 。在另外线程获取堆栈。
                    Log.e("fps-->", String.valueOf(fps));
                    // 重新设置为0.用于计算下一秒的渲染。
                    numFramesRendered = 0;

                    startFrameTimeMillis = currentFrameTimeMillis;
                }

            } else {
                // 记录当前 cpu 时间
                startFrameTimeMillis = currentFrameTimeMillis;
            }


            // 不停的循环发消息
            Choreographer.getInstance().postFrameCallback(this);

        }
    };
}
