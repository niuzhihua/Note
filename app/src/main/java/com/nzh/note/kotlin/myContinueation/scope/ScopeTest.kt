package com.nzh.note.kotlin.myContinueation.scope

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

fun main() {
    // 通过GlobalScope启动的协程 --->顶级作用域协程。
    // coroutineScope 函数内启动的协程 ---> 和父协程是协同关系。
    // supervisorScope 函数内启动的协程 --> 和父协程是主从关系。
    // 每一种作用域都对应一个协程


    // 协程1 和 协程2 没有关系
    GlobalScope.launch {
        // 协程1

        GlobalScope.launch {
            //协程2
        }

        // 不论有没有coroutineScope ，supervisorScope 协程3、4、5、6 都是协程1 的子协程。
        // 协同作用域：
        coroutineScope {
            // 协程3
            launch { }
            // 协程4
            launch { }

        }

        // 主从作用域：
        //  协程5、协程6出现异常不会取消父协程(协程1)
        //  父协程取消后 ，父协程内 supervisorScope 作用域内的协程也会取消。
        supervisorScope {
            launch { } // 协程5
            launch { } // 协程6
        }
    }
}