package com.nzh.note.kotlin.myContinueation.dispatcher

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

// 拦截器接口
interface MyDispatcher {
    fun dispatch(block:  () -> Unit)
}


/**
 *  拦截器实现类：
 *      继承类: AbstractCoroutineContextElement ，实现接口:ContinuationInterceptor
 *
 *
 */
class DispatcherImpl (val myDispatcher: MyDispatcher) :
        AbstractCoroutineContextElement(ContinuationInterceptor.Key),
        ContinuationInterceptor {


    /**
     * 拦截 Continuation : 传了一个进来，又传了一个出去。
     */
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {

        return DispatchContinuation(myDispatcher, continuation)
    }
}


/**
 * myDispatcher:
 *
 */
class DispatchContinuation<T>(val myDispatcher: MyDispatcher, val delegate: Continuation<T>)
    : Continuation<T> {
    // 在kotlin 中，如果在接口定义了一个 property(属性), 默认也就定义了 getter/setter 函数。
    // 如果是 val 属性，就只有getter.

    // 这里复写 get 函数。
    override val context: CoroutineContext
        get() = delegate.context

    override fun resumeWith(result: Result<T>) {
        // 添加一层拦截：在我们的 dispatch 的函数中调用 continuation.resumeWith 。
        // dispatch 函数由我们来控制，可以在指定线程中调用，这样就实现了线程切换。
        myDispatcher.dispatch{
            delegate.resumeWith(result)
        }
    }



}