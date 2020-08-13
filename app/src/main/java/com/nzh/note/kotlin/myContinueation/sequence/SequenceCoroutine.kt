package com.nzh.note.kotlin.myContinueation.sequence

import java.lang.IllegalArgumentException
import kotlin.coroutines.*

/**
 * 主要职责：
 *  1、将挂起函数block 和 param 传递给 最终传递给 SequenceCoroutine.
 *  2、返回 实现了Iterator 运算符的对象
 *
 *  3: block 和 myYield 函数拥有共同的receiver ,所以只能在 mySequence{ } 中调用myYield函数.
 *      mySequence{ param : Int ->
 *          this 就是 MySequenceScope<T> 实例.
 *          myYield(123)  // 等同于 this.myYield(123)
 *      }
 *
 */
fun <T> mySequence(block: suspend MySequenceScope<T>.(T) -> Unit): (T) -> MyIterator<T> {
    return { param: T ->
        IteratorImpl(block, param)
    }
}

/**
 * @param block :  挂起函数
 * @param param : 用来传递参数
 */
class IteratorImpl<T>(val block: suspend MySequenceScope<T>.(T) -> Unit, val param: T) : MyIterator<T> {

    override fun iterator(): Iterator<T> {
        return SequenceCoroutine(block, param)
    }
}

/**
 *
 *  1、创建协程, 初始化协程状态
 *  2、实现Iterator 运算符
 *  3、继承:MySequenceScope<T>,以便yield 函数限制在 sequence中调用.
 */
class SequenceCoroutine<T>(val block: suspend MySequenceScope<T>.(T) -> Unit, val param: T) :
        MySequenceScope<T>(), Continuation<Any?>, Iterator<T> {

    override val context: CoroutineContext = EmptyCoroutineContext

    var state: SequenceCoroutineState

    init {

        // 创建协程函数 createCoroutine的receiver 是无参的挂起函数。所以这里包装一层。成为一个无参的挂起函数。
        val wrapper: suspend MySequenceScope<T>.() -> Unit = { block(param) }


        // 创建协程
        val completion = wrapper.createCoroutine(this, this)

        // 初始化协程状态
        state = SequenceCoroutineState.NotReady(completion)
    }

    /**
     * 1:挂起协程.
     * 2: 更新协程状态
     */
    override suspend fun myYield(value: T) = suspendCoroutine<Unit> { continuation ->

        state = when (state) {
            is SequenceCoroutineState.NotReady -> {
                SequenceCoroutineState.Ready(continuation, value)
            }

            is SequenceCoroutineState.Ready<*> -> {
                throw IllegalArgumentException("协程状态Ready,已经yield了.")
            }

            is SequenceCoroutineState.Done -> {
                throw IllegalArgumentException("协程状态done,不能yield")
            }

        }

    }

    fun resume() {
        if (state is SequenceCoroutineState.NotReady) {
            // 执行 恢复 操作
            (state as SequenceCoroutineState.NotReady).continuation.resume(Unit)
        }
    }

    override fun hasNext(): Boolean {
        // 先执行一下恢复操作
        resume()
        // 如果协程未结束 ,表示 有元素 在yield
        return state != SequenceCoroutineState.Done
    }

    override fun next(): T {

        when (state) {
            is SequenceCoroutineState.NotReady -> {
                resume()
                return next()
            }

            is SequenceCoroutineState.Ready<*> -> {
                // 强转一次
                val currentState = state as SequenceCoroutineState.Ready<T>
                state = SequenceCoroutineState.NotReady(currentState.continuation)

                return currentState.value
            }

            is SequenceCoroutineState.Done -> {
                throw IllegalArgumentException("协程状态done, next 无效")
            }

        }
    }


    // 协程执行完成会回调
    override fun resumeWith(result: Result<Any?>) {
        // 协程置为 结束状态, 调用hasNext 时 返回false.
        state = SequenceCoroutineState.Done

        // 由于协程 挂起函数 yield 返回Unit, 执行不需要结果, 所以这里是Unit
        val r = result.getOrThrow()

        println("---end---${r}")

    }

}


class S(val name: String) {

    override fun toString(): String {
        return "name:${name}"
    }
}

