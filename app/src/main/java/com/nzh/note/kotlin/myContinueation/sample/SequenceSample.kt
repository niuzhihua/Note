package com.nzh.note.kotlin.myContinueation.sample

import com.nzh.note.kotlin.myContinueation.sequence.S
import com.nzh.note.kotlin.myContinueation.sequence.mySequence


fun main() { // main 函数没有线程调度器

    // 0、sequence 中不能调用delay.不能切换线程。只能在单线程中使用。
    // 1、sequence函数作用：创建协程 ，并初始化协程状态 .
    //                      返回 SequenceImpl对象，这个对象实现了iterator运算符。

    // 2、实现 iterator 运算符：这样就可以迭代(foreach) 返回的数据了。
    // 2、如何实现iterator运算符?  定义运算符函数：operator fun iterator():Iterator<Int>

    // 3、yield 只能在 sequence(block:(t)->Unit) 中调用，意味着 yield函数 和 block 有共同的receiver.
    //     sequence{
    //           这样才能 把 yield 函数限制在 sequence 中调用.
    //          yield(123)
    //      }


    // 4、协程状态：这里用密封类实现，没用常量 。 (带值的状态 用密封类实现)
    //     class NotReady:协程体还没有发送(yield)数据， 需要协程继续执行协程体，所以需要Continuation 成员
    //     class Ready:协程体发送(yield)了数据，已经执行了yield函数发送了数据。并且挂起了。
    //                 需要协程继续执行协程体，所以需要Continuation 成员
    //     class Done: 协程执行完了，不能发送数据了. 不需要成员。

    // 5、yield 函数 ： 就是 suspendCoroutine 函数的别名.
    //          获取Continuation实例, 并 挂起协程.
    //          每调用yield函数，更新state状态，并将value 保存到 state 中。

    // 6、Iterator接口中的 next()运算符 和 hasNext运算符：
    //    hasNext():boolean 运算符逻辑：如果协程是 NotReady状态，就 恢复协程.
    //    next():T 运算符逻辑: 就是协程状态的流转逻辑.
    //             如果是 NotReady状态 ,就 恢复协程. 返回 next() 调用.(递归)
    //             如果是Ready状态, 则更新为 NotReady状态.(因为是序列,需要不停的流转),
    //              返回 Ready状态的value.
    //             如果是Done状态,不考虑,或抛异常.


    val sequence = mySequence { i: Any ->
        println(i)
        myYield(111)
        myYield(arrayOf(4, 5, 6))
        myYield(listOf(1, 2, 3))
        myYield("444")
        myYield(555.5f)
        myYield(S("haha"))
    }

    // 调用了 forEach  触发iterator hasNext ,然后开始执行协程
    sequence(0).iterator().forEach { println(it) }




    val block: suspend A.() -> Unit = {

    }
    val a: A = A()
    println(a.bb())

}

class A {

    fun aa(block: () -> Unit): String {
        block()
        println("--aa()--")
        return "result"
    }

    // 理解成 起了个别名 bb, 并传入 函数参数：lambda表达式。
    fun bb() = aa({
        println("--bb()--")
    })
}
