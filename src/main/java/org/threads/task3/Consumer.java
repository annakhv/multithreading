package org.threads.task3;

import java.util.Queue;

public class Consumer implements Runnable {

    private Queue<String> queue;

    public Consumer(final Queue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        System.out.println("queue is empty waiting to fill up");
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                    var elem = queue.remove();
                    System.out.println("consumed value is " + elem);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                    queue.notifyAll();
                }
        }
    }
}
