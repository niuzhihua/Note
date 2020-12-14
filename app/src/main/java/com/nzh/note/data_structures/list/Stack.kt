package com.nzh.note.data_structures.list

import com.nzh.note.data_structures.list.base.SingleLinkList

/**
 * 基于单链表 实现的栈
 *
 */
class Stack<T> {
    // 单链表
    val singleLinkList = SingleLinkList<T>()

    fun push(value: T) {
        singleLinkList.addFirst(value)
    }

    fun empty() = singleLinkList.isEmpty()


    fun pop(): T? {
        return singleLinkList.removeFirst()
    }

}

fun main() {
    val stack = Stack<String>()

    stack.push("1")
    stack.push("2")
    stack.push("3")

    println(stack.pop())
    println(stack.pop())
    println(stack.pop())
    println(stack.pop())


}