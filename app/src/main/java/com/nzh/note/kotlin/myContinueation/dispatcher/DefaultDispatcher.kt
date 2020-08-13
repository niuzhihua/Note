package com.nzh.note.kotlin.myContinueation.dispatcher

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 *  协程调度器：协程执行所在的线程
 *  默认：线程池中。
 *
 */
object DefaultDispatcher : MyDispatcher {

    override fun dispatch(block:  () -> Unit) {

        dispatcherPool.submit(block)
    }

}

// 用来给线程指定名字
val threadNameIndex = AtomicInteger()
// 线程池
val dispatcherPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2) { runnable ->
    Thread(runnable, "worker-thread-${threadNameIndex.getAndIncrement()}")
}