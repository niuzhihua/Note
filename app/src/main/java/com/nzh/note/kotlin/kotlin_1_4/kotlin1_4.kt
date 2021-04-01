package com.nzh.note.kotlin.kotlin_1_4

import kotlinx.coroutines.delay
import java.lang.StringBuilder
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

// kotlin1.4新特性
fun main() {

    val parent: Parent = Sub()
    println(parent.a())

    val a = "abc"

    val c = StringBuffer("abc").toString()
    println(a == c) //  java:equals
    println(a === c) // java:==


}

suspend fun a():Int{
    val callable = object :Callable<Int>{
        override fun call(): Int {
            return 12
        }

    }
    val result  = Executors.newCachedThreadPool().submit(callable)
    val r  =result.get()
    return r
}
