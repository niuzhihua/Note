package com.nzh.note.kotlin.myContinueation


typealias onMyResume<T> = (Result<T>) -> Unit

/**
 * 定义协程状态 ： 这个协程状态需要自己维护。
 *  不带值的状态可以用枚举来实现。
 *  带值的状态 用 密封类 实现。
 */
sealed class CoroutineState {

    // 未完成状态
    object InComplete : CoroutineState()

    /**
     * 已完成状态
     *  @param value : 当前协程的返回值 。
     *  @param exception 当前协程的异常 。
     */
    class Completed<T>(val value: T? = null, val exception: Throwable? = null) : CoroutineState()

    // 等待回调状态： join 的时候 可能会设置 为 这个状态。
    class CompletedHandler<T>(val onMyResumeParam: onMyResume<T>): CoroutineState()


    /**
     * 通知 已设置为 已完成状态
     */
    fun <T> notifyStateCompleted(result: Result<T>) {

    }

}