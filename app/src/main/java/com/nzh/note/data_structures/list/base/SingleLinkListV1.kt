package com.nzh.note.data_structures.list.base

import java.util.*


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
 */
@Deprecated("")
class SingleLinkList<T> {

    val head: Node<T> = Node()   // 头节点：指向链表头
    val tail: Node<T> = Node()   // 尾节点：指向null

    var size: Int = 0   // 链表中元素的个数

    init {
        head.next = tail
    }

    /**
     * 判断链表是否为空
     */
    fun isEmpty(): Boolean {
        return head.next == tail
    }


    /**
     * 从链表头部添加
     */
    fun addFirst(value: T) {
        // 生成一个节点
        val item = Node(value)

        if (isEmpty()) { // 如果链表为空，则在头节点和尾节点之间插入元素
            head.next = item
            item.next = tail
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

        // 生成一个节点
        val item = Node(value)

        if (isEmpty()) {
            head.next = item
            item.next = tail
        } else {
            // 在最后一个节点 插入 item : 定位到最后一个节点，然后插入

            var tempNode: Node<T>? = head.next!! // 当前指针 ： 指向头节点的下一个节点
            // 遍历 直到最后一个节点
            while (tempNode?.next != tail) { // 循环条件: 当前节点的 下一个节点不为 尾节点
                tempNode = tempNode!!.next    // 更新指针 ： 指向下一个节点
            }

            // 此时的 tempNode 就是 尾节点的前一个节点
            tempNode.next = item
            item.next = tail

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
            while (tempNode?.next != tail) {

                if (index == i + 1) {   // index 位置的前面
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

        while (tempNode.next != tail) {
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
        while (tempNode.next!!.next != tail) {
            tempNode = tempNode.next!!  // 向后移一个节点
        }
        // tempNode 就是倒数第二个 节点
        val result = tempNode.next!!.value  // 最后一个节点的值
        tempNode.next = tail        // 删除最后一个节点
        return result
    }

    fun getFirst(): T? {
        return if (isEmpty()) null else head.next!!.value
    }

    fun getLast(): T? {
        return if (isEmpty()) null else {
            var tempNode: Node<T> = head.next!!   // 初始化为 第1个节点

            while (tempNode.next != tail) {  // 从 第2个 开始往后 循环 //循环条件：判断的是下一个元素
                tempNode = tempNode.next!!  // 跳到下一个节点
            }
            //tempNode 就是最后一个节点
            tempNode.value
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

        while (tempNode.next != tail) {    //循环条件：判断的是下一个元素

            tempNode = tempNode.next!!      // 跳到下一个节点
            i++                             // 更新计数

            if (i == index) {       // 放在 i++ 后面判断。
                return tempNode.value
            }
        }
        throw java.lang.RuntimeException("ignore")
    }

    /**
     * 添加一个线性集合：array
     */
    fun addAll(array: Array<T>) {

    }

    fun printAll() {
        if (!isEmpty()) {
            var tempNode: Node<T>? = head.next // 当前节点 ：tempNode 指向第一个节点
            // 遍历 直到最后一个节点
            do {
                print("${tempNode!!.value}->")
                tempNode = tempNode.next

            } while (tempNode?.next != tail)

            // 此时的 tempNode 就是尾节点的前一个节点
            print("${tempNode.value}->${tail.value}")
        }
    }
}

fun main() {
    val singleLinkList = SingleLinkList<Int>()
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

//    a.add("a")
//    a.add("b")
//    a.add("-")
//    a.add("b")
//    a.add("c")
//    a.stream().forEach {
//        print(it)
//    }

}

