package com.nzh.note.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 同步器要求：最多同时支持三个线程 运行 。
 * 很明显 ，这是一个共享锁。
 * <p>
 * 算法： 设定state初始值为3，表示最多有三个线程可以访问资源。
 * 线程拿到资源成功，则 state = 3-X.  (X>=1)
 * 线程释放资源则  state = 3+X.
 * state需要线程安全的更新
 * <p>
 * 3：表示已经有0个线程 获取到了资源。
 * 2：表示已经有1个线程 获取到了资源。
 * 1：表示已经有2个线程 获取到了资源。
 * 0：表示已经有3个线程 获取到了资源，再来线程竞争资源就阻塞。
 */
public class TribleLock implements Lock {
    final Sync sync = new Sync(3);

    public void lock() {
        // 注意调用 acquireShared
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }


    ///////////同步器///////////////

    private static final class Sync extends AbstractQueuedSynchronizer {

        public Sync(int initValue) {
            if (initValue < 0) {
                throw new IllegalStateException("不能刚创建同步器就是阻塞的状态");
            }
            setState(initValue);
        }


        @Override
        protected int tryAcquireShared(int arg) {
            for (; ; ) {
                int currentState = getState();
                int tempState = currentState - arg;
                if (tempState < 0) {    // state状态异常(小于0)： 就return，阻塞。
                    // 小于0:表示获取state 失败，阻塞
                    return tempState;
                }

                // state状态正常(大于等于0)：则自旋
                if (compareAndSetState(currentState, tempState)) {
                    // 线程安全的设置成功，则获取state成功。
                    return tempState;
                }

//                if (tempState < 0 || compareAndSetState(currentState, tempState)) {
//                    return tempState;
//                }

            }

        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (; ; ) {
                int currentState = getState();
                int tempState = currentState + arg;
                // state状态异常：抛出异常
                if (tempState < 0) {
                    throw new IllegalStateException("state状态异常");
                }
                // state状态正常(大于等于0)：则自旋
                if (compareAndSetState(currentState, tempState)) {
                    return true;
                }

            }
        }

        ///////////////////////////////////

        final ConditionObject newCondition() {
            return new ConditionObject();
        }


    }


}
