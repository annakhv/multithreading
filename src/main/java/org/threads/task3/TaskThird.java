package org.threads.task3;

import java.util.LinkedList;
import java.util.Queue;

public class TaskThird implements Runnable {
    @Override
    public void run() {
        Queue<String> q = new LinkedList<>();
        var producer1 = new Producer(q, 50);
        var producer2 = new Producer(q, 50);
        var consumer1 = new Consumer(q);
        var consumer2 = new Consumer(q);
        var consumer3 = new Consumer(q);
        var consumer4 = new Consumer(q);
        Thread threadProducer1 = new Thread(producer1);
        Thread threadProducer2 = new Thread(producer2);
        Thread threadConsumer1 = new Thread(consumer1);
        Thread threadConsumer2 = new Thread(consumer2);
        Thread threadConsumer3 = new Thread(consumer3);
        Thread threadConsumer4 = new Thread(consumer4);
        threadProducer1.start();
        threadProducer2.start();
        threadConsumer1.start();
        threadConsumer2.start();
        threadConsumer3.start();
        threadConsumer4.start();
    }

    public static void main(String[] args) {
        Thread task3 = new Thread(new TaskThird());
        task3.start();
    }
}
