package com.nzh.note.kotlin.myContinueation.sample

import com.nzh.note.kotlin.myContinueation.myDelayWithCancel
import com.nzh.note.kotlin.myContinueation.myLaunch
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


// suspend main 和 main 区别： main 没有调度器
suspend fun main() {


    // 使用自己的异常处理器
    val job = myLaunch {
        println("1")
        val r = test()      // test 挂起函数不能响应取消，所以会等待值传回来。
        println("2-$r")
        myDelayWithCancel(2000)  //myDelayWithCancel 挂起函数可以响应取消

        println("3")         // 不打印
    }
    println("-->${job.isActive}")
    job.myCancel()
    println("-->${job.isActive}")
    job.join()
    println("4")  // 不打印


}

suspend fun test() = suspendCoroutine<String> { continuation ->

    thread(isDaemon = true) {
        Thread.sleep(1500)
        continuation.resume("10086")
    }
}







