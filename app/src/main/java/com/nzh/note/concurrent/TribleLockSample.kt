package com.nzh.note.concurrent


fun main() {
    val tribleLock = TribleLock()   // 限制最多有3个线程可以获取到锁

    for (i in 0..9) {
        // 线程每个一秒打印一下自己名字
        val thread = Thread(object : Runnable {
            var counter: Long = 0;
            override fun run() {
                while (true) {
                    try {
                        tribleLock.lock()
                        println("${Thread.currentThread().name}:${counter++}")
                        Thread.sleep(2000)      //获取到锁的线程 休眠一会儿
                    } finally {
                        tribleLock.unlock()
                    }
                }
            }
        })

        thread.start()

    }
}