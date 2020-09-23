package com.nzh.note.kotlin.myContinueation

import com.nzh.note.kotlin.myContinueation.cancel.CancelState
import com.nzh.note.kotlin.myContinueation.cancel.suspendCancellableCoroutine
import com.nzh.note.kotlin.myContinueation.context.MyJob
import com.nzh.note.kotlin.myContinueation.context.OnCancel
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.cancel
import java.lang.IllegalArgumentException
import java.util.concurrent.CancellationException
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

abstract class AbstractCoroutine<T>
(context: CoroutineContext) : MyJob, Continuation<T> {

    // 协程状态
    protected val state = AtomicReference<CoroutineState>()

    override val context: CoroutineContext = context + this


    val parentJob = context[MyJob]


    // 初始化协程状态
    init {
        // 设置为未完成状态
        state.set(CoroutineState.InComplete)

        // 如果满足取消条件，则取消协程。
        parentJob?.invokeOnCancel {
            cancel()
            println(" fun cancel is invoked ...")
        }
    }

    override val isActive: Boolean
        get() = state.get() is CoroutineState.InComplete

    // 当前 Continuation 所在的协程的 状态 ，是否为 完成状态。
    override val isCompleted: Boolean =
            state.get() is CoroutineState.Completed<*>

    /**
     *  block.startCoroutine(continuation 实例) ,协程执行完后，会回调到此函数。
     */
    override fun resumeWith(result: Result<T>) {
        println("--resume with ---")

        // getAndSet : Atomically sets to the given value and returns the old value.
        // 返回的是 旧的值 , 并更新为 新值：Completed
        val oldState = state.getAndSet(CoroutineState.Completed(result.getOrNull(), result.exceptionOrNull()))

        when (oldState) {
            // join 的时候 可能会设置 为 这个状态。
            is CoroutineState.CompletedHandler<*> -> {
                println("--CompletedHandler---")
                // 此时的 currentState 就是 join/await 的时候设置的。
                // 执行 handler 函数 ， 恢复协程。
                // 注意 函数类型 的值(函数体) 是： {continuation.resume...} ,因此执行这行代码后还会 执行 resumeWith 函数。
                // 虽然返回Unit, 但是其函数体是 {continuation.resumexxx}
                // 所以当其执行后，会调用到 resumeWith 函数。在resumeWith函数中 将返回值 传给state
                (oldState as CoroutineState.CompletedHandler<T>).onMyResumeParam(result)
            }

            is CoroutineState.Completed<*> -> {
                // do Nothing
            }
            is CoroutineState.CancellingState -> {
                // do Nothing
            }

        }

        // 协程的异常处理
        (state.get() as CoroutineState.Completed<*>).exception?.let {

            when (it) {
                // 协程取消发送的异常，不用处理。
                is CancellationException -> {
                    //do nothing
                }
                else -> {
                    println("---e---")
                    // 交给 子类处理
                    myHanldeException(it)
                }
            }
        }


    }

    /**
     * 用来处理自己的异常
     */
    protected open fun myHanldeException(throwable: Throwable) {}

    /**
     * 用来处理子协程的异常
     */


    /**
     * 等待协程结束 :
     *      join 实现原理：执行一次 挂起 和 恢复
     *
     *  1、如果协程状态是 已完成，则 什么都不做。
     *  2、如果协程状态是 未完成，则 挂起。挂起就是通过添加一个 挂起函数回调 来实现。
     *  3、在 挂起回调函数中 再次更新协程状态 。
     *
     *          如果是 未完成状态(InComplete) ,则 设置为 CompletedHandler .状态。
     *          如果不是 未完成状态(InComplete) ，则一定是 已完成( Completed )状态
     *
     *  4、在 挂起回调函数中 判断：如果是已完成状态 ，则 调用 resume 恢复
     *
     *      continuation.resume(Unit)
     *
     */
    override suspend fun join() {
        println("join:${state.get()}")
        when (state.get()) {
            is CoroutineState.Completed<*> -> {

                val currentCallingJobState = coroutineContext[MyJob]?.isActive ?: return
                if (!currentCallingJobState) {
                    throw CancellationException("Coroutine is cancelled.")
                }
                return
            }

            is CoroutineState.InComplete,
            CoroutineState.CancellingState -> {
                return joinSuspend()
            }

        }
    }

    /**
     *  suspendCoroutine 函数含义：
     *  1、获取 continuation 实例
     *  2、挂起当前协程。
     *
     *  在回调函数中再次对协程状态更新：
     *      如果是未完成状态，更新为 未完成 InComplete。
     *      如果是已完成状态，不用更新。
     *
     */
    private suspend fun joinSuspend() = suspendCancellableCoroutine<Unit> { cancelContinuation ->
        doOnCompleted { result ->
            println("-----------x---")
            cancelContinuation.resume(Unit)
        }

        // 取消 挂起函数 join
        cancelContinuation.addCancelBlock {
            // do nothing
        }

    }


    /**
     * @paramon Completed 此函数作用是用来恢复协程。
     */
    protected fun doOnCompleted(onMyResumeParam: onMyResume<T>) {
        println("---doOnCompleted---")

        // compareAndSet ：
        // true: state == CoroutineState.InComplete  ，那么就设置为 CompletedHandler 。
        // false: state != CoroutineState.InComplete, 此处，如果 不等于InComplete状态，就一定是 Completed状态。
        if (!state.compareAndSet(CoroutineState.InComplete, CoroutineState.CompletedHandler(onMyResumeParam))) {

            val currentState = state.get()
            when (currentState) {

                is CoroutineState.Completed<*> -> {
                    // 调用onCompleted

                    (currentState as CoroutineState.Completed<T>).let {

                        val result = when {
                            it.value != null -> Result.success(it.value)
                            it.exception != null -> Result.failure(it.exception)
                            else -> throw IllegalStateException("Won't happen!")
                        }
                        // onCompleted 函数类型 ： 执行恢复操作。
                        onMyResumeParam(result)
                    }
                }
                is CoroutineState.CancellingState -> {
                    myCancel()
                }
                else -> throw IllegalArgumentException("invalid state ${currentState}")

            }
        }
    }


    /**
     * 若满足取消条件，则执行 block
     */
    override fun invokeOnCancel(block: OnCancel) {

        (state.get() as? CoroutineState.CancellingState)?.let {
            println(" onCancel block has bean setted...")
            block()
        }

    }

    /**
     *  取消协程
     */
    override fun myCancel() {

        //1、 执行状态流转：
        //    未完成状态 --> 取消状态。
        //    完成状态 --> do Nothing
        //    取消状态 --> do Nothing

        state.compareAndSet(CoroutineState.InComplete, CoroutineState.CancellingState)

        // 2、执行取消操作
        if (state.get() is CoroutineState.CancellingState) {
            cancel()
            println(" coroutine has bean caneled")
        }

    }


    // 返回协程的名字
    override fun toString(): String {
        return "${context[CoroutineName]?.name}"
    }
}

