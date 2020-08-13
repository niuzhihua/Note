package com.nzh.note.kotlin.myContinueation.dispatcher

import android.os.Handler
import android.os.Looper

object UIDispatcher : MyDispatcher {

    val handler = Handler(Looper.getMainLooper())


    override fun dispatch(block: () -> Unit) {
        handler.post(block)
    }

}