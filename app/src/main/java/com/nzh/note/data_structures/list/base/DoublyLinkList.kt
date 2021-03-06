package com.nzh.note.data_structures.list.base

import java.util.concurrent.locks.AbstractQueuedSynchronizer


/**
 *  双向链表：
 *
 *      删除：删除某个结点Node 不需要 知道其前驱结点，而单链表需要。
 */
class DoublyLinkList<T> {
    // 静态内部类
    class Node<T> {
        var prev: Node<T>? = null   // 指向前节点
        var next: Node<T>? = null   // 指向后节点
        var value: T? = null
    }

    val head: Node<T> = Node()  // 哨兵节点
    var tail: Node<T>? = null  // 指向尾节点

    fun isEmpty(): Boolean = head.next == null

    fun addFirst(value: T) {

    }

    fun addLast(value: T) {

    }

    fun getFirst(): T {
        TODO()
    }

    fun getLast(): T {
        TODO()
    }

    fun removeFirst(): T {
        TODO()
    }

    fun removeLast(): T {
        TODO()
    }

    fun addAt(value: T, index: Int) {

    }

    fun getAt(index: Int): T {
        TODO()
    }

    fun removeAt(index: Int): T {
        TODO()
    }

}

fun main() {



}

