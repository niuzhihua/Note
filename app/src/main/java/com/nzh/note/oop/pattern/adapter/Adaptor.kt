package com.nzh.note.oop.pattern.adapter

/**
 * 适配接口
 */
interface ITarget {
    fun newMethod()
    fun newMethod2(old: String, newParam: String): String
}

/**
 * 被 适配的 接口
 */
open class Adaptee {
    fun oldMethod() {
        println("过时的函数实现")
    }

    fun oldMethod2(s: String): String {
        return "过时的函数实现"
    }
}

// 类适配器
class ClassAdaptor : Adaptee(), ITarget {
    override fun newMethod() {

        super.oldMethod()
    }

    override fun newMethod2(s: String, newParam: String): String {
        super.oldMethod2(s)
        return "新的实现"
    }
}

/**
 * 对象适配器
 */
class ObjectAdaptor(val adaptee: Adaptee) : ITarget {

    override fun newMethod() {
        adaptee.oldMethod()
    }

    override fun newMethod2(old: String, newParam: String): String {
        adaptee.oldMethod2(old)
        return newParam
    }

}

fun main() {
//    适配器模式分类 ：
//          类适配器  ： 利用类继承的方式实现
//          对象适配器 ：利用对象组合的方式实现

//      选择：
//           如果需要适配的接口(Adaptee) 不多， 选择那个都可以。
//           如果需要适配的接口(Adaptee) 多 ，且和 ITarget接口定义 相似， 推荐用类适配器
//           如果需要适配的接口(Adaptee) 多 ，且和 ITarget接口定义 不相似，推荐对象适配器

//    适配器模式作用 ：将 不兼容的接口转为兼容的接口

//        应用场景： 适配器模式是一种 补救策略。
//            1、隔离有缺陷的接口设计 ：比如 第三方 SDK 中的接口。
//            2、统一多个类的接口设计
//            3、替换依赖的接口


    val classAdaptor = ClassAdaptor()
    classAdaptor.oldMethod()   // 旧的实现 还暴露着
    classAdaptor.newMethod()   // 新的实现

    val old = Adaptee()
    val objectAdaptor = ObjectAdaptor(old) //旧的实现隐藏了
    objectAdaptor.newMethod()       // 新的实现
}
