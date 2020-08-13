package com.nzh.note.kotlin.myContinueation

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

val pool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()) { runnable ->
    Thread(runnable, "DelayThread_").apply { isDaemon = true }
}



// 1、调用挂起函数 时必然 有一个 continuation 实例。
// 通过 suspendCoroutine 函数来获取 挂起函数的 continuation 实例。
//  2、定义 myDelay 函数，返回值 Unit, 函数体作为参数传给suspendCoroutine
suspend fun myDelay(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) =
        suspendCoroutine<Unit> {

               pool.schedule(
                            {
                                println("-...${time}s-")
                                it.resume(Unit)
                            },    // runnable 可用 lambda 表达式 代替。
                            time,
                            unit
                    )
        }


// 1、定义 a 函数，函数体作为参数 传递给 b函数。
// 2、定义 a 函数 ，返回值为 b 函数的返回值。
fun a() = b {
    println("-a-body-")
}

fun c() {
    val result = a()
}

fun b(block: () -> Unit): Int {
    println("-b-body-")
    block.invoke()
    return 3
}
