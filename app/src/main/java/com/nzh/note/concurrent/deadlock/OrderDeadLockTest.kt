package com.nzh.note.concurrent.deadlock

import com.nzh.note.concurrent.D
import java.util.*

fun main() {
//    deadLockSample1()    // OK
//    deadLockSample2()  // OK

    println(D.COUNT_BITS)
    println(D.CAPACITY)
    println("-----------")
    println(D.RUNNING)
    println(D.SHUTDOWN)
    println(D.STOP)
    println(D.TIDYING)
    println(D.TERMINATED)
    println("-----------")
    println(D.t2)
    println(D.t3)
    println("-----------")
    D().testRetry()
}

fun deadLockSample1() {
    val order = OrderDeadLock()
    val thread1 = Thread(Runnable { order.left2Right() })
    val thread2 = Thread(Runnable { order.right2Left() })
    thread1.start()
    thread2.start()
}

fun deadLockSample2() {
    val accountA = Object()
    val accountB = Object()
    val order = OrderDeadLock2()
    val thread1 = Thread(Runnable { order.transferMoney(accountA, accountB) })
    val thread2 = Thread(Runnable { order.transferMoney(accountB, accountA) })
    thread1.start()
    thread2.start()
}