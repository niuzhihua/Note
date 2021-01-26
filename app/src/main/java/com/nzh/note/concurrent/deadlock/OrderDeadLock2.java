package com.nzh.note.concurrent.deadlock;

class OrderDeadLock2 {

    public void transferMoney(Object left, Object right) {

        synchronized (left) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("获取了锁" + left + " ,等待锁" + right + "....");
            synchronized (right) {
                doSomeThing();
            }
        }
    }

    private void doSomeThing() {
        System.out.println("doSomeThing-" + Thread.currentThread().getName());
    }

}


