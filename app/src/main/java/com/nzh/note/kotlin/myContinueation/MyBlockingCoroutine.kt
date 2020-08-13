package com.nzh.note.kotlin.myContinueation

import com.nzh.note.kotlin.myContinueation.dispatcher.DispatcherImpl
import com.nzh.note.kotlin.myContinueation.dispatcher.MyDispatcher
import java.util.concurrent.LinkedBlockingDeque
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine


// 定义任务函数
typealias MyTask = () -> Unit

/**
 *  Blocking 协程 : 处理(消费) 消息队列中的 消息 MyTask
 */
class BlockingCoroutine<T> (context: CoroutineContext, private val queue:  LinkedBlockingDeque<MyTask>) : AbstractCoroutine<T>(context) {


    /**
     *  当协程状态是 未完成 状态时  ，取出 MyTask ,并调用。
     *  最后返回协程状态
     */
    fun joinBlocking() :T{

        while (!isCompleted) {
            val task = queue.take()
            task.invoke()
            println("task is taked&invoked : ${task.hashCode()}")
        }

        return (state.get() as CoroutineState.Completed<T>).let {
            it.value ?:throw it.exception!!
        }
    }
}

/**
 * 消息队列：存放 MyTask
 *
 *  implements MyDispatcher : 每次线程切换时 ，就添加任务
 */
class MyBlockQueue : LinkedBlockingDeque<MyTask>(), MyDispatcher {

    override fun dispatch(block: () -> Unit) {
        println("task is added : ${block.hashCode()}")
        offer(block)
    }

}


