package com.nzh.note.kotlin.operator

import kotlin.coroutines.suspendCoroutine

class Point(val x: Int, val y: Int) {

    override fun toString(): String {
        return "(${x},${y})"
    }

    operator fun plus(p: Point): Point {  // + 号运算符重载
        val tempx = this.x + p.x
        val tempy = this.y + p.y
        return Point(tempx, tempy)
    }

    operator fun minus(p: Point): Point {
        return Point(this.x - p.x, this.y - p.y)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Point) {
            return this.x == other.x && this.y == other.y
        }
        return false
    }


}

operator fun Point.invoke(block: () -> Unit) {
    block()
}

operator fun Point.invoke(block: (Point) -> Unit) {
    block(this)
}

operator fun Point.invoke(str: String) {
    println("()运算符重载(String类型参数)${str}-")
}

operator fun String.invoke(block: (String) -> Unit) {
    println("()运算符重载(有参函数参数)-")
    block(this)
}

operator fun String.invoke(block: () -> Unit) {
    println("()运算符重载(无参函数参数)-")
    block()
}

operator fun String.invoke(str: String) {
    println("()运算符重载(String类型参数)${str}-")
}

operator fun String.unaryPlus() {        // 一元运算符重载
    println("一元运算符重载${this}")
}

// 运算符重载
// https://kotlinlang.org/docs/reference/operator-overloading.html

fun main() {
    // 一元运算符重载
     + "giao"

    // 二元运作符重载

    // 自定义 类的运算符 重载
    val p0 = Point(4, 6)
    val p1 = Point(1, 2)
    val p2 = Point(3, 4)

    val p3 = p1 + p2
    val p4 = p2 - p1

    println(p0.equals(p3)) // true
    println(p3.toString()) // (4,6)
    println(p4.toString()) // (2,2)

    // () 操作符 重载

    p0 { thiz ->
        println("--定义在类外面--${thiz}")
    }
    p0 { ->
        println("--定义在类外面--")
    }

    p0("hehe")

    "abc"{ it ->
        println(it)
    }
    "abc"{ ->

        println("abc")

        + "giaogiaogiao"
    }

    "abc"("haha")


}


