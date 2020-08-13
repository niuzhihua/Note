package com.nzh.note.kotlin.myContinueation.flow

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

suspend fun main() {


        // 1、 flow(流) 的创建 和 消费
        // 从集合创建flow
        val flow = listOf(1, 3, 5, 6).asFlow()
        val flow2 = setOf(1, 2, 3, 4).asFlow()
        val flow3 = flowOf("a", 3, 4.0f)
        // 从chanel 创建flow
//    val channel = Channel<String>()
//    channel.consumeAsFlow().collect {  }

        flow3.collect {
            println("1-->${Thread.currentThread().name} ${it}")
        }
        flow3.flowOn(Dispatchers.IO).collect {
            println("2-->${Thread.currentThread().name} ${it}")
        }

        // 2、flow中元素的生成

        // 生成元素时不切换调度器的方式
        val f = flow {
            emit(123)
            println("emit in ${Thread.currentThread().name}  ")
            emit("123")
            println("emit in ${Thread.currentThread().name}  ")

            // 错误写法
//        withContext(Dispatchers.IO){
//            emit(444)
//        }
        }

        // 和 sequence一样，只有调用了 collect 才触发flow的调用
        f.collect {
            println("3-->${Thread.currentThread().name} ${it}")
        }

        // 3、生成元素时切换调度器的方式

        val f2 = channelFlow {
            send(123)  // 当前线程(main)发送元素
            withContext(Dispatchers.IO) {
                // IO线程发送元素
                send(456)
            }

        }

        f2.collect { println("4-->${Thread.currentThread().name} ${it}") }


}