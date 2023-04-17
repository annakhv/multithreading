package org.threads.task2;

import java.util.Collection;
import java.util.concurrent.ForkJoinPool;

public class SumPrinter implements Runnable{
    Collection<Integer> collection;
    private AdderToCollection adder;


    public SumPrinter(final Collection<Integer> collection) {
        this.collection = collection;
    }

    @Override
    public void run() {
        ForkJoinPool pool=ForkJoinPool.commonPool();
        while (true) {
            try {
                synchronized (collection){
                    System.out.println("pool size is "+pool.getPoolSize());
                    int sumVal=pool.invoke(new SummerRecursiveTask(collection));
                    System.out.println("current sum is " + sumVal);
                    Thread.sleep(500);
               }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
