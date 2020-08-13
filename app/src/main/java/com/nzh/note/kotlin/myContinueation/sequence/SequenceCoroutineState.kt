package com.nzh.note.kotlin.myContinueation.sequence

import kotlin.coroutines.Continuation

sealed class SequenceCoroutineState{

    //没有调用yield 的状态： continuation: 用来继续执行
    class NotReady(val continuation: Continuation<Unit>) : SequenceCoroutineState()


    //调用了yield的状态： continuation： 用来继续执行， value: yield 函数发送的值。
    class Ready<T>(val continuation: Continuation<Unit>, val value: T) : SequenceCoroutineState()

    // 协程结束状态
    object Done : SequenceCoroutineState()

}



