package org.threads.task4;

import java.util.Random;

public class TaskFour implements Runnable {
    @Override
    public void run() {
        BlockingObjectPool<Integer> blockingQueue = new BlockingObjectPool<>(30);
        Thread producer = new Thread(() -> {
            while (true) {
                var random = new Random().nextInt();
                try {
                    blockingQueue.put(random);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    blockingQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        producer.start();
        consumer.start();
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new TaskFour());
        thread.start();
    }
}
