package com.nzh.note.kotlin.myContinueation.sample

import com.nzh.note.kotlin.myContinueation.myAsync
import com.nzh.note.kotlin.myContinueation.myDelay

suspend fun main() {

    println("start.....")
    val defferred1 = myAsync {

        println("---1---")
        myDelay(2000)
        println("---1.1---")

        "{ key1:value1, key2:value2 }"
        // 模拟异常
//        "${5 / 0}"
    }
    println("--------")
    val defferred2 = myAsync {

        println("---2---")
        myDelay(4000)
        println("---2.2---")

        "{ key4:value4, key5:value6 }"
        // 模拟异常
//        throw IllegalArgumentException("nullpointer exception")
    }
    println("---3---")
    try {
        val result1 = defferred1.await()
        println("---4---")
        val result2 = defferred2.await()   // 等待时间长的在最后?
        println("end.....")
        println(result1)
        println(result2)
    } catch (e: Exception) {
        println("---e---")
        e.printStackTrace()
    }

}