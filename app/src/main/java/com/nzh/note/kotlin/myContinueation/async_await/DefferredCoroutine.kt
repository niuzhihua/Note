package com.nzh.note.kotlin.myContinueation.async_await

import com.nzh.note.kotlin.myContinueation.AbstractCoroutine
import com.nzh.note.kotlin.myContinueation.CoroutineState
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DefferredCoroutine<T>(context: CoroutineContext) : AbstractCoroutine<T>(context), DefferredJob<T> {

    override suspend fun await(): T {
        // 如果是未完成状态 ：则挂起
        // 如果完成状态: 就返回 协程执行的结果(值或抛的异常)
        // 如果是等待完成状态(join) : 则挂起?

        val currentState = state.get()
        when (currentState) {
            is CoroutineState.InComplete -> {
                return awaitSuspend()
            }
            is CoroutineState.Completed<*> -> {

                // 如果值为空，则返回异常
                // 如果value 为空，则exception 一定不为空。

                return (currentState.value as T?) ?: throw currentState.exception!!
            }
            is CoroutineState.CompletedHandler<*> -> {
                println("--CompletedHandler--")
                return awaitSuspend()
            }

        }

    }

    // 调用suspendCoroutine 函数挂起
    private suspend fun awaitSuspend() = suspendCoroutine<T> { continuation ->

        doOnCompleted { result ->
            continuation.resumeWith(result)

        }

    }


}