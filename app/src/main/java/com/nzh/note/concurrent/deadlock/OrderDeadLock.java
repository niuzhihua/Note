package com.nzh.note.concurrent.deadlock;

class OrderDeadLock implements Runnable {
    boolean b = false;
    private final Object left = new Object();
    private final Object right = new Object();

    public void left2Right() {

        synchronized (left) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("获取了锁left,等待锁right....");
            synchronized (right) {
                doSomeThing();
            }
        }
    }

    public void right2Left() {
        synchronized (right) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("获取了锁right,等待锁left....");
            synchronized (left) {
                doSomeThing();
            }
        }
    }

    private void doSomeThing() {
        System.out.println("doSomeThing-" + Thread.currentThread().getName());
    }

    @Override
    public void run() {

        if (b) {
            left2Right();
        } else {
            right2Left();
        }
    }
}


