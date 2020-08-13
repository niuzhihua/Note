package com.nzh.note.kotlin.myContinueation.context

import kotlin.coroutines.CoroutineContext

interface MyJob : CoroutineContext.Element {

    companion object Key2 : CoroutineContext.Key<MyJob>

    override val key: CoroutineContext.Key<*>
        get() = MyJob

    /**
     * 等待协程结束
     *  等待协程结束的实现原理：当协程状态是未完成时，添加一个 挂起回调函数。
     *
     *   为什么是挂起函数？
     *  添加挂起回调函数 通过 suspendCoroutine来实现。因此 join 函数也需要是 挂起函数。
     */
    suspend fun join()

    /**
     * 取消协程
     */
    fun cancel()
}