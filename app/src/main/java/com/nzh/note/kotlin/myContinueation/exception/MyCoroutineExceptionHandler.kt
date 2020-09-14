package com.nzh.note.kotlin.myContinueation.exception

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 *  异步任务的异常处理器，协程中未捕获的异常就会交给这个handler 处理。
 */
interface MyCoroutineExceptionHandler : CoroutineContext.Element {

    // 伴生对象：就是Key
    //      名字为 MyCoroutineExceptionHandler
    //      实现了 CoroutineContext.Key 接口

    // 使用伴生对象时，用的名字是CoroutineExceptionHandler
    companion object : CoroutineContext.Key<MyCoroutineExceptionHandler>

    /**
     * 用来处理协程未处理的异常
     * @param coroutineContext  协程上下文环境
     * @param throwable 异常
     */
    fun myHandleException(coroutineContext: CoroutineContext, throwable: Throwable) {
        println("--abstract fun myHandleException--")
    }
}

/**
 * 创建异常处理器
 * @param handler  : 是一个函数类型， 返回异常处理器
 */
inline fun createCoroutineExceptionHandler(crossinline myHandler: (CoroutineContext, Throwable) -> Unit)
        : MyCoroutineExceptionHandler {

    // MyCoroutineExceptionHandler 就是伴生对象的名字。

    // 匿名内部类：实现了 MyCoroutineExceptionHandler 接口。

    return object : AbstractCoroutineContextElement(MyCoroutineExceptionHandler), MyCoroutineExceptionHandler {
        override fun myHandleException(coroutineContext: CoroutineContext, throwable: Throwable) {
            // 调用 异常处理函数
            myHandler(coroutineContext, throwable)
        }

    }
}