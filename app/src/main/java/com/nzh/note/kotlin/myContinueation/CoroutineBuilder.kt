package com.nzh.note.kotlin.myContinueation

import com.nzh.note.kotlin.myContinueation.async_await.DefferredCoroutine
import com.nzh.note.kotlin.myContinueation.async_await.DefferredJob
import com.nzh.note.kotlin.myContinueation.context.MyJob
import com.nzh.note.kotlin.myContinueation.dispatcher.DispatcherImpl
import com.nzh.note.kotlin.myContinueation.exception.MyCoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine


class StandaloneCoroutine(context: CoroutineContext) : AbstractCoroutine<Unit>(context) {

    // 重写异常处理函数
    override fun myHanldeException(throwable: Throwable) {

        // 如果有自己的异常处理器，则调用处理器的函数 处理异常。
        val result = context[MyCoroutineExceptionHandler]?.myHandleException(context, throwable)

        // 否则 用当前线程的 常处理器 来 处理异常。
        result ?: Thread.currentThread().let {
            println("--没有自己的异常处理器--")
            it.uncaughtExceptionHandler.uncaughtException(it, throwable)
            throwable.printStackTrace()
        }
    }
}


/**
 * 一个协程启动后有 两个Continuation 对象。
 *
 *  传入的Continuation  ：block ， 实际上是一个 SuspendLambda 。 SuspendLambda是Continuation子类。
 *  返回的Continuation ： StandaloneCoroutine
 *
 */
fun myLaunch(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> Unit): MyJob {

    val standardCoroutine = StandaloneCoroutine(context)

    // 调用 挂起函数 的 startCoroutine 函数，并传入 continuation 实例。
    // 最终回调到 continuation的 resumeWith 函数。
    block.startCoroutine(standardCoroutine)

    return standardCoroutine
}

fun <T> runBlocking(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> T): T {
    // 创建消息队列
    // 包装消息队列 到 CoroutineContext
    // 创建协程 BlockingCoroutine
    // 用挂起函数 启动协程
    // 开启消息处理

    val queue = MyBlockQueue()

    val newContext = context + DispatcherImpl(queue) //CoroutineContext实现了运算符重载

    val blockCoroutine = BlockingCoroutine<T>(newContext, queue)

    // block带泛型--->blockCoroutine带泛型--->
    block.startCoroutine(blockCoroutine)

    return blockCoroutine.joinBlocking()
}

/**
 * async、await 成对使用。
 *  async 方式使用协程，
 *  await获取返回值，可以理解为 有返回值的 join.
 */
fun <T> myAsync(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> T): DefferredJob<T> {

    val defferredCoroutine = DefferredCoroutine<T>(context)
    block.startCoroutine(defferredCoroutine)

    return defferredCoroutine
}
