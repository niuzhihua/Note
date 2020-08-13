package com.nzh.note.kotlin.myContinueation.sample

import com.nzh.note.kotlin.myContinueation.myDelay
import com.nzh.note.kotlin.myContinueation.myLaunch
import com.nzh.note.kotlin.myContinueation.runBlocking



fun main() = runBlocking {
    println("1")
    val job = myLaunch {
        println("2")
        myDelay(2000)
        println("3")
    }
    println("4")
    job.join()  // 一个挂起点 ： 添加到 queue 里面
    println("5")
    myDelay(3000)   // 一个挂起点 ：添加到 queue 里面
    println("6")

}
