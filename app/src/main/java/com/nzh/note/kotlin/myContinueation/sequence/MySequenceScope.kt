package com.nzh.note.kotlin.myContinueation.sequence

abstract class MySequenceScope<T> {

    /**
     * 发送数据
     * 挂起函数，用来挂起协程。
     *
     * 为什么将myYield函数放入 MySequenceScope<T> 类中?
     *  限制调用:
     *      通过 挂起函数 block: suspend MySequenceScope<T>.(T)->Unit  得知, block 和 myYield
     *      有着共同的receiver. 所以myYield 函数可以限制在 mySequencce{ } 中调用。
     *
     *  mySequencce 函数肯定返回一个 MySequenceScope<T> 实例.
     */
    abstract suspend fun myYield(value: T)
    //
//    val block :suspend ()->Unit  = {}
}