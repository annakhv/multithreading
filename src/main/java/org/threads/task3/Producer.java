package org.threads.task3;

import java.util.Queue;
import java.util.UUID;

public class Producer implements Runnable {
    private Queue<String> queue;
    private int maxSize;

    public Producer(final Queue<String> queue, final int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.size() == maxSize) {
                    try {
                        System.out.println("queue size is maxsize " + maxSize);
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                    var randomText = UUID.randomUUID()
                            .toString();
                    queue.add(randomText);
                try {
                    Thread.sleep(170);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                queue.notifyAll();
            }
        }
    }
}
