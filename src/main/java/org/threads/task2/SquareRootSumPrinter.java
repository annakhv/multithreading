package org.threads.task2;

import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SquareRootSumPrinter implements Runnable {
    private Collection<Integer> collection;

    private static Logger logger= Logger.getLogger(SquareRootSumPrinter.class.getName());

    public SquareRootSumPrinter(final Collection<Integer> collection) {
        this.collection = collection;
    }

    @Override
    public void run() {
        ForkJoinPool pool=ForkJoinPool.commonPool();
        while (true) {
            synchronized (collection){
               double sumVal= pool.invoke(new SquareRootSumRecursiveTask(collection));
                logger.log(Level.INFO,"current square root of squared sum is " + Math.sqrt(sumVal)+" in collection of size "+collection.size());
                Thread.yield();

            }

        }
    }
}
