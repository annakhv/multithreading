package org.threads.task2;

import java.util.Collection;
import java.util.concurrent.ForkJoinPool;

public class SquareRootSumPrinter implements Runnable {
    private Collection<Integer> collection;

    public SquareRootSumPrinter(final Collection<Integer> collection) {
        this.collection = collection;
    }

    @Override
    public void run() {
        ForkJoinPool pool=ForkJoinPool.commonPool();
        while (true) {
            try {
                synchronized (collection){
                   double sumVal= pool.invoke(new SquareRootSumRecursiveTask(collection));
                    System.out.println("current square root of squared sum is " + Math.sqrt(sumVal));
                    Thread.sleep(500);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
