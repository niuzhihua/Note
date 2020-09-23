package com.nzh.note.kotlin.myContinueation.context

import kotlin.coroutines.CoroutineContext

typealias OnCancel = () -> Unit

interface MyJob : CoroutineContext.Element {

    companion object  : CoroutineContext.Key<MyJob>

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

    val isActive: Boolean

    val isCompleted: Boolean

    /**
     * 取消协程
     */
    fun myCancel()

    /**
     * 如果满足取消条件，则 执行 block
     */
    fun invokeOnCancel(block: OnCancel)

}