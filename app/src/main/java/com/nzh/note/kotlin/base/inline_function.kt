package com.nzh.note.kotlin.base


// 1、定义内联函数
inline fun sayHello(str: String) {

}

/**
 * 调用say 这个高阶函数 要
 *      1、创建 block (lambda表达式)。
 *      2、调用 block().
 *  因此高阶函数 更适合定义为内联函数。
 */
inline fun say(block: () -> Unit) {
    var start = System.currentTimeMillis()
    block()
//    say2(block)
    println(System.currentTimeMillis() - start)
}

fun say2(block: () -> Unit) {
    block()
}

inline fun noneLocalReturn(block: () -> Unit) {
    block()
}

fun localReturn(block: () -> Unit) {
    block()
}

inline fun inlineRunable(crossinline block: () -> Unit): Runnable {
    return object : Runnable {
        override fun run() {
            // 如果block里面直接return,那么 就返回了一个run 函数，而不是 Runable对象。所以编译器禁止这样调用。
            // 如果要这样使用，需要将block 用 crossinline 关键字修饰。表示禁止在 block 中return.
            block()  // 需要加 crossinline 修饰。
        }
    }
}

var name: String = "xxx.yyy"
var firstName: String
    get() = name
    set(value) {
        name = value
    }
var lastName: String
    inline get() = name
    inline set(value) {
        name = value
    }

fun main() {
    /**
     * 内联函数： 常用于 对高阶函数的性能优化。
     *  1、将函数本身(函数体)内联到函数的调用处，减少了函数调用
     *  2、将函数的参数 内联到 函数调用处。
     *  3、内联函数的return
     *  4、内联函数的return的限制。
     *  5、限制：内联函数只能访问内联函数。
     */

    /**
     * 内联属性：内联属性运行时时没有这个属性的。  只是把getter/setter 函数内联了。
     */

    say {
        println("say hello")
    }

    // 4、内联函数的return的限制
    inlineRunable {
        // 禁止return ,编译报错。
//        return
        println("-inlineRunable-")
    }.run()

    // 3、普通函数 中的 return : 跳出当前localReturn 函数，main函数继续向下执行。
    localReturn {
        return@localReturn
    }
    // 3、内联函数的return : 跳出了 noneLocalReturn所在的函数，也就是main函数。因为是内联到这里的。
    noneLocalReturn {
        // 如果调用return,下面都不执行。
        return
    }
    // 不执行
    println("--------")


}



