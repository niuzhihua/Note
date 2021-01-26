package com.nzh.note.concurrent


fun main() {

    val sample = SampleCounter()
    val thread = Thread(sample)
    val thread2 = Thread(sample)
    thread.start()
    thread2.start()
    thread.join()
    thread2.join()
    println("a=${sample.a}")
    println("a2=${sample.a2}")
    println("a3=${sample.a3}")
    println("a4=${sample.a4}")

}