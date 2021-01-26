package com.nzh.note.concurrent;

import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public final class D {

    public int t1 = -8;
    public static final int COUNT_BITS = Integer.SIZE - 3;  // 29
    public static final int CAPACITY = (1 << COUNT_BITS) - 1;

    public static final int RUNNING = -1 << COUNT_BITS;    // -1
    public static final int SHUTDOWN = 0 << COUNT_BITS; // 0
    public static final int STOP = 1 << COUNT_BITS; // 1
    public static final int TIDYING = 2 << COUNT_BITS;    // 2^29
    public static final int TERMINATED = 3 << COUNT_BITS; // 3^29
    public static final int t2 = 1 << 29;
    public static final int t3 = 2 << 28;
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static int workerCountOf(int c) {

        System.out.println("workerCountOf c:" + c);
        System.out.println("workerCountOf CAPACITY:" + CAPACITY);
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {

        return rs | wc;
    }

    public D() {


    }

    public void test() {
        int c = ctl.get();
        System.out.println(c);
        System.out.println(workerCountOf(c));
    }

    public void testRetry() {
        retry:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println("i = " + i + ",j=" + j);
                if (i == 0 && j == 2) {
                    continue retry;     // break当前循环，并 continue retry修饰的循环
                }

                if (i == 1 && j == 3) {
                    break retry;      // break当前循环，并break retry修饰的循环
                }
            }
        }

        // break : 退出当前循环
    }

}


