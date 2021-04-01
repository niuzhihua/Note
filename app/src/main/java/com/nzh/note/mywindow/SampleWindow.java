package com.nzh.note.mywindow;

import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.view.Choreographer;
import android.view.Surface;
import android.view.ViewGroup;
import android.view.WindowManager;

class SampleWindow {

    //            IWindowSession
//            InputChannel
    //            InputHandler extends InputEventReceiver
    Rect mInsets = null;
    Rect mFrame = null;
    Rect mVisiableInSets = null;
    Configuration mConfig = null;
    Surface mSurface = null;
    Paint mPaint = null;
    IBinder mToken = new Binder();
    MyWindow myWindow = new MyWindow();
    WindowManager.LayoutParams mLp = new WindowManager.LayoutParams();
    Choreographer mChoreographer =  Choreographer.getInstance();

    public void run() {

    }
}
