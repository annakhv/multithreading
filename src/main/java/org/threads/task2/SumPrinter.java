package org.threads.task2;

import java.util.Collection;

public class SumPrinter implements Runnable{
    Collection<Integer> collection;

    public SumPrinter(final Collection<Integer> collection) {
        this.collection = collection;
    }

    public int sum(){
        return collection.stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public void run() {
        while (true) {
            try {
                var sumVal = sum();
                System.out.println("current sum is " + sumVal);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
