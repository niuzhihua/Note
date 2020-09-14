package com.nzh.note.kotlin.myContinueation.sample

import com.nzh.note.kotlin.myContinueation.context.MyJob
import com.nzh.note.kotlin.myContinueation.exception.MyCoroutineExceptionHandler
import com.nzh.note.kotlin.myContinueation.exception.createCoroutineExceptionHandler
import com.nzh.note.kotlin.myContinueation.myDelay
import com.nzh.note.kotlin.myContinueation.myLaunch
import kotlinx.coroutines.Job
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException


// suspend main 和 main 区别： main 没有调度器
suspend fun main() {

    val exceptionHandler = createCoroutineExceptionHandler { coroutineContext, throwable ->
        println("-自己的异常处理--")
        // 注意这个key
        val key = MyCoroutineExceptionHandler
        println("协程名称：-->${coroutineContext[key]}")
        println("异常信息：-->$throwable")

    }
    // 使用自己的异常处理器
    val job = myLaunch(exceptionHandler) {
        println("---1--- ")
        myDelay(2000)
        println("---2--- ")
        throw  IllegalStateException("test exception")
        println("---3---")
    }
    println("---4---")
    job.join()
    println("---5---")


}







