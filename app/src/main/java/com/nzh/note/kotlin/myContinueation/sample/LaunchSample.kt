package com.nzh.note.kotlin.myContinueation.sample

import com.nzh.note.kotlin.myContinueation.dispatcher.DispatcherImpl
import com.nzh.note.kotlin.myContinueation.dispatcher.MyDispatchers
import com.nzh.note.kotlin.myContinueation.myDelay
import com.nzh.note.kotlin.myContinueation.myLaunch
import kotlin.concurrent.thread
import kotlin.coroutines.*

fun aa(method: () -> Unit) {
    method.invoke()
    method.haha("haha")
}

fun (() -> Unit).haha(str: String) {
    println(str)
}


fun aaImpl() {
    println("{" +
            "调用方式1")

}


fun testLaunch() {
    mylaunchtest(MyDispatchers.UI)
}

fun mylaunchtest(dispatcher: DispatcherImpl) {
    myLaunch(dispatcher) {
        println("---1----当前线程：${Thread.currentThread().name}")
        val result = getUserInfo(1000)
        println("----2--result:${result}:${Thread.currentThread().name}")

        myDelay(3000)
        println("----3--${Thread.currentThread().name}")
        val result2 = getUserInfo(2000)

        println("----4---")
        println("----5--result2:${result2}:${Thread.currentThread().name}")
        println("----6---")
        var result3 = getUserInfo2()
        println("----7--result3:${result3}:${Thread.currentThread().name}")
    }

}

// suspend main 和 main 区别： main 没有调度器
suspend fun main() {

//    mylaunchtest(MyDispatchers.Default)  // OK

        val job = myLaunch {
            println("---1----当前线程：${Thread.currentThread().name}")
            val result = getUserInfo(1000)
            println("----2--result:${result}:${Thread.currentThread().name}")

            myDelay(3000)  // 守护线程
            println("----3--${Thread.currentThread().name}")
            val result2 = getUserInfo(2000)

            println("----4---")
            println("----5--result2:${result2}:${Thread.currentThread().name}")
            println("----6---")
            var result3 = getUserInfo2()
            println("----7--result3:${result3}:${Thread.currentThread().name}")
        }
        // suspend 函数
        job.join()
        println("----8---")  // 不执行

}

suspend fun getUserInfo(time: Long): String = suspendCoroutine {
    thread(isDaemon = true) {
        Thread.sleep(time)
        println("************>getUserInfo-${Thread.currentThread().name}")
        it.resume("abc")
    }
}


suspend fun getUserInfo2(): String {

    println("************>getUserInfo2 ${Thread.currentThread().name} ")
    return "abc"
}



