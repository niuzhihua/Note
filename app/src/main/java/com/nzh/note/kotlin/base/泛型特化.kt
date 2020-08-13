package com.nzh.note.kotlin.base

/**
 *  内联特化：用reified 关键字修饰的泛型，并且定义在 inline 函数中。
 *      这样使用泛型时，函数会被内联到调用处 ，并且可以使用传入的类型。
 *
 */
inline fun <reified T> genMethod(t: T) {

//    val a = T()  // 由于不知道 T 类型有没有 无参构造，所以即使加了内联特化，也无法使用。

    // 加了内联特化后，就可以获取类型了。 这行代码会别内联到 调用处。
    val jclass = T::class.java

}

fun <T> genMethod2(t: T) {
//    val a = T()    // 由于泛型擦除 ，不能使用T类型
//    val jclass =  T::class.java  // 由于泛型擦除 ，不能使用T类型
}


open class Parent<T>(val value: T)
// 编译后 类型擦除
//open class Parent(val value:Object)

fun <T : Comparable<T>> maxOf(a: T, b: T): T {
    return if (a > b) a else b
}
//  编译后 类型擦除
//fun <Comparable> maxOf(a: Comparable, b: Comparable): Comparable {
//    return if (a > b) a else b
//}


