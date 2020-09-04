package com.nzh.note.kotlin.base

// 1、inline：不仅可以内联函数内部的代码，还可以内联函数参数。
//        内联函数参数 ： 减少了临时对象的创建。

//    由于java不支持函数类型，所以kotlin 中的函数类型 在 jvm中 如何实现呢？
//    实际上还是用一个对象来作为 函数的载体(receiver)，使用对象来执行 函数类型的代码。
//    inline使用场景，有高级函数的地方加inline.

// 每次调用hello 函数时，都会创建一个对象来执行 lambda表达式里的代码。用完后抛弃。
// 查看字节码便知道。
fun hello(block: () -> Unit) {
    println("hello body")
}
// 2、noinline :
//      不内联函数的函数参数，来摆脱inline内联造成的 函数类型参数不能当对象使用。

//    由于函数类型的参数，本质上是个对象，因此可以将这个参数(对象)当作 函数 和 对象(值)来使用。
//    但是如果函数被inline修饰，则 函数的 函数参数(本质是对象) 就不存在了，所以如果还想 把
//    函数参数当作对象使用， 则需要加 noinline关键字。
//    也就是说，noinline 使用的前提一定是在 inline 修饰的函数的 函数参数中。


fun hello2(block: () -> Unit): () -> Unit {
    // 将这个参数当作函数 使用
    block()
    // 将这个参数当作对象(值) 使用
    // 但是如果这个函数加了 inline，则 block参数就不是对象了，会被内联到调用处。
    // 所以这里就不能当对象(值)来用了。也就不能return了。
    return block
}

// 在 inline 修饰的函数的 函数参数中 使用 noinline
inline fun hello22(noinline block: () -> Unit): () -> Unit {
    return block
}

// 3、crossinline :  内联函数内 ，被crossinline修饰的函数类型参数 ，不能在lambda表达式中
//                   使用return

// 也就是说 crossinline加强了 函数类型参数的 内联优化。让函数类型参数 可以被当作对象调用。

inline fun hello3(crossinline block: () -> Unit) {
    runOnUiThread {
        // 在内联函数hello3中，block 函数类型属于间接调用，如果block内有return，
        // 这个return 返回到哪里？runOnUiThread 是不是内联函数不确定。所以 不允许
        // 这样间接调用。如果想间接调用，则用 crossinline 修饰block
        block()
    }
}

inline fun hello33(block: () -> Unit) {
    // 直接调用block，没有了 间接调用
    block()
}


fun main() {
    // hello被调用时，会创建一个对象来执行 lambda表达式里的代码
    hello {
        println("block lambda body")
    }

    // 由于执行lambda表达式会有对象创建，这样在循环中执行就有了性能问题。
    for (i in 0..99) {
        hello {
            println("block lambda body")
        }
    }

    // 如果加了 inline, 则把hello 函数 和 hello 函数的参数类型 的代码 一起内联 到调用处。
    // 避免了创建对象
    for (i in 0..99) {
        println("hello body")
        println("block lambda body")
    }

    hello3 {
        println("crossinline 允许间接调用，不允许 return")
//        return  // 不允许 return
    }

    hello33 {
        println("没有了间接调用，可以 return")
        return
    }

}

fun runOnUiThread(block: () -> Unit) {
    block()
}
