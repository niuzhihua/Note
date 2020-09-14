package com.nzh.note.kotlin.base

// 伴生对象： 就理解为java中的 静态方法/静态变量 即可。
class A {
    // 生产非静态的Field
    @JvmField
    val s: String = "abc"

    // 伴生对象只能定义一个。
    // 伴生对象 不写名字和类名一样。
    companion object B {

        // 生产静态的 Field
        @JvmField
        val staticField: Int = 1

        fun a() {
            println("a")
        }

        // 加上JvmStatic后就和Java中的静态方法一样了。
        @JvmStatic
        fun b() {
            println("b")
        }
    }
}

class AA {
    // 伴生对象:名字为BB
    companion object BB: Runnable {
        override fun run() {
            println("run")
        }

        fun bb() {
            println("bb")
        }
    }
}

fun main() {

    AA.BB.bb()
    AA.BB.run()
    // 调用伴生对象A的函数
    A.a()
    A.b()
    // 伴生对象A的 属性
    println(A.staticField)
    println(A().s)
}