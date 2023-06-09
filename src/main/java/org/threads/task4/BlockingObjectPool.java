package org.threads.task4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingObjectPool<T> {

    private Queue<T> queue = new LinkedList<>();
    private final int size;
    Lock lock = new ReentrantLock();
    Condition conditionNotEmpty=lock.newCondition();
    Condition conditionNotFull=lock.newCondition();

    /**
     * Creates filled pool of passed size
     *
     * @param size of pool
     */
    public BlockingObjectPool(int size) {
        this.size = size;
    }

    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param element to be taken back to pool
     */
    public void put(T element) throws InterruptedException {
        try {
            lock.lock();
            while (queue.size() >= size) {
                System.out.println("queue is full");
                conditionNotFull.await();
            }
            queue.add(element);
            System.out.println("element " + element + " has been put");
            Thread.sleep(50);
            conditionNotEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public T take() throws InterruptedException {
        T element = null;
        lock.lock();
        try {
            while (queue.isEmpty()) {
                System.out.println("queue is empty waiting to fill up");
                conditionNotEmpty.await();
            }
            element = queue.remove();
            Thread.sleep(100);
            System.out.println("element " + element + " has been removed by thread "+Thread.currentThread().getName());
            conditionNotFull.signal();
        } finally {
            lock.unlock();
        }
        return element;
    }
}
