package com.nzh.note.kotlin.myContinueation.sample.AndroidSample

import android.os.Build
import android.util.Log
import android.view.View
import com.nzh.note.kotlin.operator.invoke
import kotlinx.coroutines.Job

/**
 * 利用View 的 onDetachedFromWindow 接口自动取消协程。
 *
 *  proxy: 使用proxy成员去代理Job接口
 *  AutoDispose = Job + View.OnAttachStateChangeListener
 */
class AutoDisposeJob(val view: View, val proxy: Job) :
        Job by proxy, View.OnAttachStateChangeListener {

    // 设置添加监听  和 设置移除监听
    init {
        if (isViewAttached()) {
            // 设置监听
            view.addOnAttachStateChangeListener(this)
        } else {
            // 取消协程
            cancel()
            Log.e("-->", "cancel")
        }
        // 注册一个回调，当协程执行完时 (异常情况)。
        invokeOnCompletion {
            view.removeOnAttachStateChangeListener(this)
        }

    }

    override fun onViewDetachedFromWindow(v: View?) {
        // 1、取消协程
        cancel()
        // 2、移除监听
        view.removeOnAttachStateChangeListener(this)
        Log.e("-->", "ViewAttached corourine canceled")
    }


    fun isViewAttached() = Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT
            && view.isAttachedToWindow || view.windowToken != null


    override fun onViewAttachedToWindow(v: View?) {
        // do nothing
    }
}