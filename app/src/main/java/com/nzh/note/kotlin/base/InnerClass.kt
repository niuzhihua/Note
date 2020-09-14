package com.nzh.note.kotlin.base

// 1、内部类的定义
// 2、内部object(kotlin特有的),定义出来的都是静态的
class Outer{

    // inner 关键字：表示非静态内部类 ： 持有外部类的实例引用
    inner class InnerClass{
    }

    //不写inner关键字表示 静态内部类
    class StaticInner{
    }


    object StaticObj1  // 静态的
    object Obj2   // 静态的

    //
    fun test(){
        // 匿名内部类 定义方式。
        val runnable = object :Runnable{
            override fun run() {

            }
        }
        // 匿名内部类 可以实现多个接口。
        // runnableCloneable : 是一个交叉类型 Runnable&Cloneable
        val runnableCloneable = object :Runnable,Cloneable{
            override fun run() {

            }
        }

    }

}