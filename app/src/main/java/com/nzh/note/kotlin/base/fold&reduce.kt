package com.nzh.note.kotlin.base

import java.lang.StringBuilder

fun main() {
    // 集合的聚合操作：fold,reduce
    fold_reduce()

}

private fun fold_reduce() {
    // 0： 累加器的初始值
    val foldRight1 = listOf(1, 3, 5).foldRight(0) { acc, i ->
        acc + i
    }
    println("foldRight1:${foldRight1}")

    // StringBuilder()： 累加器的初始值
    val builder = listOf("1", "2", "3", "4").foldRight(StringBuilder()) { item, builder ->

        builder.append(item)
    }
    println("foldRight2:${builder}")

    // haha: 累加器的初始值
    val fold = listOf("1", "2", "3", "4", "5").fold("haha") { acc, i ->
        acc + i
    }
    println("fold:${fold}")


    val reduce1 = listOf("1", "2", "3", "4", "5").reduce { acc: String, i: String ->
        acc + i
    }
    val reduce2 = listOf("1", "2", "3", "4", "5").reduce { acc, i ->
        i + acc
    }

    println("reduce1:${reduce1}")
    println("reduce2:${reduce2}")
}