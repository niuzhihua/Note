package com.nzh.note.oop.pattern.adapter

interface A {   // 零散的接口：比如来自第三方库。
    fun checkA(str: String): String
}

interface B {    // 零散的接口：比如来自第三方库。
    fun checkB(str: String): String
}

interface C {    // 零散的接口：比如来自第三方库。
    fun checkC(str: String, str2: String)
}

interface IUnited {  // 将零散接口统一起来： 统一 A、B、C
    fun check(str: String, str2: String): String
}

class Adaptor4A(val a: A) : IUnited {   // 适配接口
    override fun check(str: String, str2: String): String {
        println("适配A接口")
        return a.checkA(str)
    }
}

class Adaptor4B(val b: B) : IUnited {    // 适配接口
    override fun check(str: String, str2: String): String {
        println("适配B接口")
        return b.checkB(str)
    }
}

class Adaptor4C(val c: C) : IUnited {    // 适配接口
    override fun check(str: String, str2: String): String {
        println("适配C接口")
        c.checkC(str, str2)
        return "OK"
    }

}

class Manager {     // 统一调用。
    val list = mutableListOf<IUnited>()
    fun add(iUnited: IUnited) {
        list.add(iUnited)
    }

    fun check(str: String, str2: String): String {
        val result = mutableListOf<String>()
        list.forEach {
            result.add(it.check(str, str2))
        }
        return result.toString()
    }
}

fun main() {
    // 如果需要新添加一个接口，那么Manager类不用改动，只需要 创建一个Adaptor4X ，并添加到Manager即可。

    val manager = Manager()
    manager.add(Adaptor4A(object : A {
        override fun checkA(str: String): String {
            // A 已经有的实现
            return "A的实现"
        }

    }))
    manager.add(Adaptor4B(object : B {
        override fun checkB(str: String): String {
            //  B已经有的实现
            return "B的实现"
        }

    }))
    manager.add(Adaptor4C(object : C {
        override fun checkC(str: String, str2: String) {
            // C 已经有的实现
            println("C的实现")
        }
    }))
    val s1 = "sss"
    val s2 = "ssss"
    manager.check(s1, s2)
}


