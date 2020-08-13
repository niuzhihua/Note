package com.nzh.note.kotlin.base

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

// 1、接口代理：
// 2、属性代理：
// 3、属性代理-observable
// 4、自定义属性代理
class Stu(val name: String, val age: Int) {

    // 2、lazy 代理了 firstName 属性的 getter函数。
    val firstName by lazy {
        var i = name.indexOf(".")
        name.substring(0, i)
    }

    // 3、每次 调用属性的 setter 时，就会执行lambda表达式。
    var state by Delegates.observable(1) { property, oldValue, newValue ->
        println("${oldValue} - ${newValue}")
    }
    var state2: String by Delegates.observable("abc") { property, oldValue, newValue ->
        println("${oldValue} - ${newValue}")
    }

    // 3.1 、  true ：  将设置新的值。set to the new value
    //         false： 返回 oldValue
    var vetoable by Delegates.vetoable(1) { property, oldValue, newValue ->

        println("${oldValue} - ${newValue}")
        true
    }
}

/**
 *  4、自定义属性代理 ： 实现属性代理 参考 ReadWriteProperty 接口的定义就可以了。
 *      对于 var 属性需要实现 setValue、getValue 两个运算符
 *      对于 val 属性来说，只需要实现 getValue 运算符就可以了。
 *   ReadWriteProperty<in R, T>{
 *
 *
 *      public operator fun getValue(thisRef: R, property: KProperty<*>): T
 *
 *
 *      public operator fun setValue(thisRef: R, property: KProperty<*>, value: T)
 *
 *
 *   }
 */

class State {
    // 定义只读变量 的代理
    val read by StatePropertyDelegate(1)
    val read1: Int by StatePropertyDelegate(2)

    // 定义可读可写变量 的代理
    var rw by StatePropertyDelegate(0)
}

class StatePropertyDelegate(var v: Int) {

    /**
     * 如果不确定代理的类型，写 Any 。 如果 确定，写相应类型 就可以了。
     */
    public operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        println("getValue:${v}")
        return v
    }

    public operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        println("setValue:${v}")
        this.v = value
    }
}


fun main() {
    // 接口代理
    val apiImpl = ApiImpl()
    val api = Wrapper(apiImpl)
    val api2 = Wrapper2(apiImpl)

    api.b()
    api2.b()

    // 属性代理
    println("---------Delegates.observable----------")
    var stu = Stu("jerry", 11)
    stu.state++   // 每次调用setter,就会执行getter
    stu.state++    // 每次调用setter,就会执行getter

    stu.state2 += "1"
    stu.state2 += "1"
    println(stu.state2)
    println("---------Delegates.vetoable----------")

    stu.vetoable = 8
    stu.vetoable = 1
    stu.vetoable = 1
    stu.vetoable = 8
    println("stu.vetoable=${stu.vetoable}")

    println("----------------------")
    // 自定义属性代理
    val s = State()
    s.rw = 2
    s.rw = 4
    s.rw = 6
    println("var : ${s.rw}")

    println("-------------------")

    println(s.read)
    println(s.read1)


}

interface IApi {
    fun a()
}

class ApiImpl : IApi {
    override fun a() {
        println(" function a() is implemented")
    }
}

// 代理类 : 常规写法。
class Wrapper(val api: IApi) : IApi {

    fun b() {
        api.a()
    }

    override fun a() {
        api.a()
    }
}

// 1、 接口代理的 kotlin 写法。
class Wrapper2(val api: IApi) : IApi by api {
    fun b() {
        api.a()
    }
}
