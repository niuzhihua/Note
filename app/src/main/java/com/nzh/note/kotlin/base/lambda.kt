package com.nzh.note.kotlin.base


// lambda 表达式 本质上 就是匿名函数
//1、 lambda 的定义
//2、 lambda 表达式的返回值就是 最后一行的返回值。
//3、 java 当中的lambda表达式就是 接口的语法糖，而kotlin中的lambda 是匿名函数。
//4、 使用FuncionX类型定义lambda 表达式

val lambda = {
    // 当没有参数时，不用写参数列表
    println("---lambda表达式1：可以类型推导出类型(Unit)，就不用写类型了---")
}
// 等价于上面写法.
val lambda2: () -> Unit = {
    println("---lambda表达式: 直接定义了 lambda表达式类型。---")
    "hello"
}

// 参数为 String类型的 lambda(匿名函数)
val lambda3 = { str: String ->
    println("---lambda表达式2: 可以类型推导出类型(String)，就不用写类型了---")
}
//  等价于上面写法.
val lambda4: (String) -> Unit = { str: String ->
    println("---lambda表达式---")
    999.0f
}

val lambda5 = { str: String ->
    println("---lambda表达式---")
    "result"
}
// 等价于上面写法.
val lambda6: (String) -> String = { str: String ->
    println("---lambda表达式---")
    "result"
}


val lambda7: Function1<String, String> = { str: String ->
    println("---lambda表达式---")
    "result"
}
val lambda8: Function1<String, Unit> = {
    println("---lambda表达式---")
    "result"
    999
}
val lambda9: Function1<Unit, String> = {
    println("---lambda表达式---")
    "result"
}
val lambda10: Function1<Unit, Unit> = {
    println("---lambda表达式---")
    "result"
}

val lambda11: Function6<Int, Int, Int, Int, Int, Int, String> = { i1, i2, i3, i4, i5, i6 ->

    "${i1}${i2}${i3}${i4}${i5}${i6}-6个参数-"
}
val lambda12: Function9<Int, Int, Int, Int, Int, Int, Int, Int, Int, Unit> = { i1, i2, i3, i4, i5, i6, i7, i8, i9 ->
    println("${i1}${i2}${i3}${i4}${i5}${i6}${i7}${i8}${i9}-9个参数-")
}

suspend fun main() {
    println(lambda11(1, 2, 3, 4, 5, 6))
    lambda12(1, 2, 3, 4, 5, 6, 7, 8, 9)
}







