package com.nzh.note.data_structures.list.base

import android.os.ConditionVariable
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Exchanger
import java.util.concurrent.Semaphore


/**
 *  总结：
 *  单链表 实现的操作有：
 *      size计数的作用： 凡是和索引相关的操作 都会涉及到 计数。
 *                  根据索引添加、查找等等。
 *      添加：
 *          addFirst、addLast :添加到链表头部和尾部
 *          addAt : 根据索引添加
 *
 *      查找：getFirst、getLast ： 获取头节点和尾节点 数据
 *            get(index) : 根据索引查找
 *
 *   了解了单链表的实现后，就很容易来实现栈，队列了。
 *
 *   实现队列： 排队进 addLast ，从头出 removeFirst。
 *   实现栈： 从头进 addFirst，从头出 removeFirst。
 *
 *   为什么哨兵节点在链表头部 ？
 *   为什么链表尾部不需要哨兵节点 ？
 *
 */
class SingleLinkList2<T> {

    /**
     * 头节点 / 哨兵节点
     */
    val head: Node<T> = Node()

    /**
     *  指向尾节点，tail.next 永远为null
     *  注意不是  哨兵节点
     */
    var tail: Node<T> = Node()

    var size: Int = 0   // 链表中元素的个数

    /**
     * 判断链表是否为空
     */
    fun isEmpty(): Boolean {
        return head.next == null
    }

    /**
     * 从链表头部添加
     */
    fun addFirst(value: T) {
        // 生成一个节点
        val item = Node(value)

        if (isEmpty()) { // 如果链表为空，则在头节点 后面 插入
            head.next = item
            tail = item     // 指向尾节点
        } else {
            // 在头节点后面插入一个item节点
            item.next = head.next
            head.next = item
        }
        // 记录元素个数
        size++
    }

    /**
     * 从链表尾部添加
     */
    fun addLast(value: T) {

        if (isEmpty()) {
            addFirst(value)
        } else {
            // 生成一个节点
            val item = Node(value)

            // 链接 最后一个节点
            tail.next = item
            // 指向最后一个节点
            tail = item
        }
        // 元素个数 +1
        size++
    }

    /**
     * 按照指定索引添加元素
     * @param index 索引
     */
    fun addAt(value: T, index: Int) {
        if (index > size || index < 0) {
            throw IndexOutOfBoundsException("索引越界")
        }

        if (index == 0 || size == 0) {
            addFirst(value)
        } else if (index == size) {
            addLast(value)
        } else {
            // 将元素插入 index 位置：遍历链表 找到 index 位置
            var i = 0
            var tempNode: Node<T>? = head.next
            while (tempNode?.next != null) {   // tail 不是哨兵节点，不要用在 条件判断中

                if (index == i + 1) {    // 命中则插入到 tempNode后面
                    // 插入 item.
                    val item = Node(value)
                    item.next = tempNode?.next
                    tempNode?.next = item

                    // 不要忘了size 计数
                    size++
                    break
                }
                // 继续计数遍历
                i++
                tempNode = tempNode?.next
            }

        }

    }

    /**
     * 删除成功：返回被删除的元素 。
     * 删除是吧：返回null
     */
    fun removeFirst(): T? {
        return if (isEmpty()) null else {
            val result = head.next!!.value
            head.next = head.next!!.next
            result
        }
    }

    /**
     * 删除实现：
     * 1、定位到 与value值相等的节点的 前一个节点A。
     * 2、定位到 与value值相等的节点的 后一个节点B。
     * 3、 A->B
     *
     * 返回是否删除成功
     */
    fun remove(value: T): Boolean {
        if (isEmpty()) {
            return false
        }

        var tempNode = head
        // tempNode 上
        // tempNode.next 中
        // tempNode.next!!.next  下
        while (tempNode.next != null) {
            if (tempNode.next!!.value == value) {
                tempNode.next = tempNode.next!!.next
                size--
                return true
            }
            tempNode = tempNode.next!!      // 向后移一个节点
        }
        return false
    }

    /**
     * 删除尾节点：定位到 倒数第二个 节点。
     * 删除成功：返回被删除的元素 。
     * 删除是吧：返回null
     */
    fun removeLast(): T? {
        if (isEmpty()) return null
        if (size == 1) return removeFirst()

        var tempNode = head.next!!
        while (tempNode.next != tail) {
            tempNode = tempNode.next!!  // 向后移一个节点
        }
        // tempNode 就是倒数第二个 节点
        val result = tempNode.next!!.value  // 保存最后一个节点的值
        tempNode.next = null        // 删除最后一个节点
        tail = tempNode     // 更新尾指针 tail
        return result
    }

    fun getFirst(): T? {
        return if (isEmpty()) null else head.next!!.value
    }

    fun getLast(): T? {
        return if (isEmpty()) null else {
            tail.value
        }
    }

    /**
     * 和 getLast() 函数类似
     */
    fun get(index: Int): T? {
        if (isEmpty()) return null
        if (index >= size) throw java.lang.IndexOutOfBoundsException("查询索引越界")
        if (index == 0) return getFirst()

        var tempNode: Node<T> = head.next!!
        var i = 0   // 用来在链表中计数

        while (tempNode.next != null) {

            tempNode = tempNode.next!!      // 跳到下一个节点
            i++                             // 更新计数

            if (i == index) {       // 放在 i++ 后面判断。
                return tempNode.value
            }
        }
        throw java.lang.RuntimeException("ignore")
    }


    fun printAll() {
        if (!isEmpty()) {
            var tempNode: Node<T>? = head.next // 当前节点 ：tempNode 指向第一个节点
            // 遍历 直到最后一个节点
            do {
                print("${tempNode!!.value}->")
                tempNode = tempNode.next

            } while (tempNode?.next != null)

            // 此时的 tempNode 就是尾节点    tempNode.value == tail.value
            print("${tempNode!!.value}")
        }
    }
}

fun main() {
    val singleLinkList = SingleLinkList2<Int>()
    singleLinkList.addFirst(3)
    singleLinkList.addFirst(2)
    singleLinkList.addFirst(1)

    singleLinkList.addLast(4)
    singleLinkList.addLast(5)
    singleLinkList.addLast(6)
    singleLinkList.addLast(6)
    // 插入链表中间
    singleLinkList.addAt(9, 3)
    // 插入链表两端
    singleLinkList.addAt(8, 0)
    singleLinkList.addAt(7, 8)

    singleLinkList.printAll()

    println("--------query--------")
    println("first:${singleLinkList.getFirst()}")
    println("last:${singleLinkList.getLast()}")

    println("get(0):${singleLinkList.get(0)}")
    println("get(5):${singleLinkList.get(5)}")
    println("get(9):${singleLinkList.get(9)}")
    println("--------remove--------")
    singleLinkList.remove(9)
    singleLinkList.remove(8)
    singleLinkList.remove(6)
    singleLinkList.printAll()
    println("--------removeLast--------")
    singleLinkList.removeLast()
    singleLinkList.printAll()

    val a: LinkedList<String> = LinkedList()
    val s:ConditionVariable? = null


//    a.add("a")
//    a.add("b")
//    a.add("-")
//    a.add("b")
//    a.add("c")
//    a.stream().forEach {
//        print(it)
//    }

}

