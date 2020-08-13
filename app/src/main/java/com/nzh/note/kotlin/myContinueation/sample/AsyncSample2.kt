package com.nzh.note.kotlin.myContinueation.sample

import com.nzh.note.kotlin.myContinueation.myAsync
import com.nzh.note.kotlin.myContinueation.myDelay

suspend fun main() {      // OK

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

    println("---3---")
    try {
        val result1 = defferred1.await()
        println("---4---")
        println("end.....")
        println(result1)
    } catch (e: Exception) {
        println("---e---")
        e.printStackTrace()
    }



}