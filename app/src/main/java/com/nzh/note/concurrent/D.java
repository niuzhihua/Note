package com.nzh.note.concurrent;

import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public final class D {

    private final int a;


    class Async extends AbstractQueuedSynchronizer{
    }

    public D(int a) {

        this.a = a;

    }

}


