package com.nzh.note.oop.principle

import java.lang.RuntimeException
import java.util.*

/**
 * 里氏替换原则 的定义：
 *      1、子类对象能够替换 任何地方的 父类对象。
 *      2、程序的 逻辑和正确性 不变。
 *
 *   如何判断是否违背此原则： 子类的实现是否违背 父类的规定.
 *
 *
 *    父类有哪些规定:
 *      1 : 对行为(方法/函数)功能的规定.
 *      2 : 对行为(方法/函数)的规定,包括 输入,输出,异常返回.
 *              输入/输出: 输入输出的类型子类不能变
 *              异常返回: 异常出现后 返回类型:
 *                          返回null / 返回空值 / 返回异常
 *                        比如某个函数规定,出错只会抛出 ArgumentNullException , 那子类
 *                        只允许返回 ArgumentNullException.
 *
 *      3 : 其他特殊规定 . 比如注释中的特殊规定.
 *
 *
 */
interface Parent {


    /**
     * 父类定义的功能:
     * 输入规定：int
     * 输出规定：String
     * 异常规定: 运行出错返回 IllegalArgumentException
     */
    fun doRequest(params: MutableList<Any>): String {
//        // 输入规定
//        if (params.isEmpty())
//        // 异常规定
//            throw IllegalArgumentException("param invalid")

        val random = Random()
        val result = random.nextInt(100)
        if (result % 5 == 0) {
            // 异常规定
            throw IllegalArgumentException("result invalid")
        }
        // 输出规定
        return "{$result}"
    }

}

class Child(var name: String, var age: Int) : Parent {      // 正确实现

    override fun doRequest(params: MutableList<Any>): String {
        if (!name.isEmpty()) {
            params.add(name)
            params.add(age)
        }
        return super.doRequest(params)
    }
}

class BadChild(var name: String, var age: Int) : Parent {   // 错误实现

    override fun doRequest(params: MutableList<Any>): String {
        // 与父类的异常规定不一致.
        if (name.isEmpty()) {
            throw RuntimeException("param name invalid")
        }
        params.add(name)
        params.add(age)
        return super.doRequest(params)
    }
}

class BadChild2(var id: String) : Parent {   // 错误实现

    override fun doRequest(params: MutableList<Any>): String {
        // 与父类的异常规定不一致.
        if (id.isEmpty()) {
            throw RuntimeException("param name invalid")
        }
        params.add(id)
        return super.doRequest(params)
    }
}

class Demo {
    fun request(parent: Parent) {
        val list = mutableListOf<Any>()
        val r = parent.doRequest(list)
        println(r)
    }
}

fun main() {
    // test

    val demo = Demo()
    demo.request(Child("tom", 12))
    demo.request(Child("", 12))
    demo.request(BadChild("jerry", 12))

    //
    demo.request(BadChild("", 12))
    demo.request(BadChild2(""))

}