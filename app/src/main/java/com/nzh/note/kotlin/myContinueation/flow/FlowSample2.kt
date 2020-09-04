package com.nzh.note.kotlin.myContinueation.flow

import com.nzh.note.kotlin.myContinueation.dispatcher.MyDispatchers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import java.lang.IllegalArgumentException

/**
 * 1、如果异步任务结果只有一个值，那么用挂起函数就够了；
 *    如果有多个值，例如下载进度这种，则用Flow。
 *
 * 2、创建 Flow:
 *          通过集合来创建 Flow :
 *              listOf(1, 2, 3, 4).asFlow()
 *              setOf(1, 2, 3, 4).asFlow()
 *              flowOf(1, 2, 3, 4)
 *
 * 3、Flow 也可以设定它运行时所使用的调度器。
 *   通过 flowOn 设置的调度器只对它之前的操作有影响，
 *
 * 4、Flow 的消费：使用挂起函数 collect
 *
 * 5、一个 Flow 创建出来之后，不消费则不生产，多次消费则多次生产，生产和消费总是相对应的。
 *
 * 6、异常处理 ：
 *          catch :  推荐使用 catch 函数 捕获异常
 *
 *          onCompletion : 无论前面是否存在异常，它都会被调用，参数 t 则是前面未捕获的异常.
 *
 *               推荐使用 onCompletion 函数 代替 finally，想要在流完成时执行逻辑，可以使用 onCompletion。
 *
 *          不推荐使用 try{}catch{}finally{}
 *
 *  7、末端操作符 : 由于 Flow 的消费端一定需要运行在协程当中，因此末端操作符都是挂起函数。
 *
 *          collect 是最基本的末端操作符，还有
 *              集合类型转换操作，toList、toSet 等
 *              聚合操作 ：reduce、fold 等
 *              获得单个元素的操作： single、singleOrNull、first 等
 *
 * 8、Flow 的取消 ：想要取消 Flow 只需要取消它所在的协程即可。
 *
 *      Flow 的消费依赖于 collect 这样的末端操作符，而它们又必须在协程当中调用，
 *      因此 Flow 的取消主要依赖于末端操作符所在的协程的状态。
 *
 * 9、Flow中使用调度器 注意情况：
 *          不能在 Flow 中直接切换调度器
 *          想要在生成元素时切换调度器，就必须使用 channelFlow 函数来创建 Flow
 *
 *          channelFLow{
 *              send(1)
 *              withContext(Dispatchers.IO){
 *                  send(2)
 *              }
 *
 *          }
 *
 *  10、Flow 的背压 ：生产者的生产速率高于消费者的处理速率
 *
 *       使用 conflate函数 解决背压 ：
 *              新数据会覆盖老数据
 *       使用collectLatest 函数解决背压 ：
 *              这种方式新数据 不会 覆盖老数据，而是每一个都会被处理，如果
 *              前一个还没被处理完，处理前一个数据的逻辑就会被取消。
 *
 *
 *
 *
 *
 *
 */

suspend fun main() {

    // 创建Flow
    val ints: Flow<Int> = flow {
        (1..3).forEach {
            emit(it)
            println("生产--> ${Thread.currentThread().name} : ${it}")
            delay(100)
        }
    }
    // ints 的构造逻辑会在 flowOn指定的调度器(IO 调度器)上执行。
//    ints.flowOn(Dispatchers.IO)


    //  flow的消费 ： 在 MyDispatchers.Default 调度器上
    GlobalScope.launch(MyDispatchers.Default) {

        //  flow的生产： 在 Dispatchers.IO调度器上。
        ints.flowOn(Dispatchers.IO).collect {
            println("消费--> ${Thread.currentThread().name} : ${it}")
        }

        // 重复消费 伴随着 重复生成
        ints.flowOn(Dispatchers.IO).collect {
            println("消费2--> ${Thread.currentThread().name} : ${it}")
        }
    }.join()


    // 异常处理
    flow {
        emit(3)
        emit(5 / 0)
    }.catch { t: Throwable ->
        println("catch:${t.message}")
    }.onCompletion { t: Throwable? ->
        println("finally/onCompletion:${t?.message}")
    }.collect {
        println(it)
    }

    // flow 的取消

    val job = GlobalScope.launch {

        flow {
            (1..9).forEach {
                delay(1000)
                emit(it)
            }
        }.collect { println("1-9：${it}") }
    }

    delay(5000)
    job.cancelAndJoin()  // 取消flow所在的协程

    // 背压 处理方式1
    flow {
        // 生产
        (0..99).forEach {
            emit(it)
        }
    }.conflate().collect {
        // 消费
        delay(200)
        println("conflate---> $it")
    }

    // 背压 处理方式2
    flow {
        // 生产
        (0..99).forEach {
            emit(it)
        }
    }.collectLatest {
        // 消费
        delay(200)
        println("collectLatest--> $it")
    }

}

