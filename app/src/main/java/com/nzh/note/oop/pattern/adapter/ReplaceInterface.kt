package com.nzh.note.oop.pattern.adapter

import java.util.*

// 老的接口
interface IOld {
    fun sayHello(str: String)
}

class IOldImpl : IOld {
    override fun sayHello(str: String) {
        println("hello:$str")
    }
}

class User(val iOld: IOld) {
    fun say(str: String) {
        iOld.sayHello(str)
    }
}

class INew {
    fun sayHello2(str: String) {
        println("new impl hello:$str")
    }
}

class CommonAdaptor(val iNew: INew) : IOld {   // 将 IOld 接口 替换为 INew接口。
    override fun sayHello(str: String) {
        iNew.sayHello2(str)
    }
}


fun main() {
    // 不用适配器模式的代码
    val user = User(IOldImpl())
    user.say("tom")

    // 使用 适配器的代码： 将 IOld 接口 替换为 INew接口 ，只需要将CommonAdaptor 注入 User即可。
    // 优点:调用IOld接口的地方都无需改动。
    val user2 = User(CommonAdaptor(INew()))
    user2.say("jerry")
}