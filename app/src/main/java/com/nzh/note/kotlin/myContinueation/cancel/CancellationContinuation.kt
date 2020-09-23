package com.nzh.note.kotlin.myContinueation.cancel

import com.nzh.note.kotlin.myContinueation.context.MyJob
import com.nzh.note.kotlin.myContinueation.context.OnCancel
import java.util.concurrent.CancellationException
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.intercepted
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

class CancellationContinuation<T>(private val proxyContinuation: Continuation<T>) : Continuation<T> by proxyContinuation {

    private val state = AtomicReference<CancelState>(CancelState.InComplete)

    private val cancelHandlers = CopyOnWriteArrayList<OnCancel>()

    val isCompleted: Boolean
        get() = state.get() is CancelState.Complete<*>

    val isActive: Boolean
        get() = state.get() == CancelState.InComplete

    override fun resumeWith(result: Result<T>) {
        state.updateAndGet { prev ->
            when (prev) {
                CancelState.InComplete -> {
                    proxyContinuation.resumeWith(result)
                    CancelState.Complete(result.getOrNull(), result.exceptionOrNull())
                }
                is CancelState.Complete<*> -> throw IllegalStateException("Already completed.")
                CancelState.Cancelled -> {
                    CancellationException("Cancelled.").let {
                        proxyContinuation.resumeWith(Result.failure(it))
                        CancelState.Complete(null, it)
                    }
                }
            }
        }
    }

    /**
     *  向集合中 添加 用于 取消挂起函数(delay等)的 block.
     */
    fun addCancelBlock(block: OnCancel) {
        cancelHandlers += block
    }

    fun getResult(): Any? {
        installCancelHandler()
        // 获取挂起函数的结果
        return when (val currentState = state.get()) {
            CancelState.InComplete -> COROUTINE_SUSPENDED
            is CancelState.Complete<*> -> {
                (currentState as CancelState.Complete<T>).let {
                    it.exception?.let { throw it } ?: it.value
                }
            }
            CancelState.Cancelled -> throw CancellationException("Continuation is canceled.")
        }
    }

    /**
     * 获取协程，若满足取消条件，则执行 doCancel 函数
     */
    private fun installCancelHandler() {

        if (!isActive) return
        val parent = proxyContinuation.context[MyJob] ?: return
        parent.invokeOnCancel {
            doCancel()
        }
    }

    /**
     * 1、更新 cancel协程 的状态
     * 2、执行 cancelHandlers集合中的 函数， 这些函数用来取消挂起函数。例如delay
     *
     */
    private fun doCancel() {
        state.updateAndGet { prev ->
            when (prev) {
                CancelState.InComplete -> {
                    CancelState.Cancelled
                }
                is CancelState.Complete<*>,
                CancelState.Cancelled -> {
                    prev
                }
            }
        }

        cancelHandlers.forEach(OnCancel::invoke)
        cancelHandlers.clear()
    }
}

suspend inline fun <T> suspendCancellableCoroutine(
        crossinline block: (CancellationContinuation<T>) -> Unit
): T = suspendCoroutineUninterceptedOrReturn { c: Continuation<T> ->
    val cancellationContinuation = CancellationContinuation(c.intercepted())

    block(cancellationContinuation)
    cancellationContinuation.getResult()
}