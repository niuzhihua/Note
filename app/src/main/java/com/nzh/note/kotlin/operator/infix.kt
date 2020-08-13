package com.nzh.note.kotlin.operator

// 中缀表达式
// 删除后缀
infix fun String.del(str: String): String {
    val index = this.lastIndexOf(str)
    return this.substring(0,index)
}

fun main(){
    val str1 = "D:/txt/a/abc/a.txt"
    val str2 = "D:/html/a/abc/a.html"

    val result1 = str1 del ".txt"  // 加了infix 关键字后的写法
    val result11 = str1.del(".txt")

    println(result1)
    println(result11)
    println(str2 del ".html")

}