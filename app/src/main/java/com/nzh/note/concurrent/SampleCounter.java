package com.nzh.note.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

class SampleCounter implements Runnable {

    int a = 0;
    AtomicInteger a2 = new AtomicInteger(0);
    int a3 = 0;
    public int a4 = 0;

    private void add() {

        for (int i = 0; i < 100; i++) {
            a++;
        }
    }


    private void add2() {
        for (int i = 0; i < 100; i++) {
            a2.getAndIncrement();
        }
    }

    private void add3() {
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                a3++;
            }
        }
    }

    @Override
    public void run() {
        add();  // 线程不安全。
        add2(); // 使用原子类 保证原子性、可见性。从而保证线程安全。
        add3(); // 使用锁保证原子性、可见性。从而保证线程安全。
        a4 = add4(a4); // 利用对象的不变性 实现线程安全。

    }


    public int add4(final int a) {
        if (a >= 200) {
            return a;
        } else {
            return add4(a + 1);
        }
    }
}
