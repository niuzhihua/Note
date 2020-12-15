package com.nzh.note.data_structures.list

import com.nzh.note.data_structures.list.base.SingleLinkList
import com.nzh.note.data_structures.list.base.SingleLinkList2

/**
 * 基于单链表实现的队列
 */
class Queue<T> {

    val singleLinkList = SingleLinkList2<T>()

    // 入队列
    fun enqueue(value: T) {
        singleLinkList.addLast(value)
    }

    // 出队列
    fun dequeue(): T? {
        return singleLinkList.removeFirst()
    }

}

fun main() {
    val queue = Queue<String>()
    queue.enqueue("1")
    queue.enqueue("2")
    queue.enqueue("3")

    println(queue.dequeue())
    println(queue.dequeue())
    println(queue.dequeue())
    println(queue.dequeue())

}