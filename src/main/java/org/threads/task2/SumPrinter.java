package org.threads.task2;

import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SumPrinter implements Runnable{
    Collection<Integer> collection;
    private AdderToCollection adder;
    private static Logger logger= Logger.getLogger(SumPrinter.class.getName());

    public SumPrinter(final Collection<Integer> collection) {
        this.collection = collection;
    }

    @Override
    public void run() {
        ForkJoinPool pool=ForkJoinPool.commonPool();
        while (true) {
            synchronized (collection){
                int sumVal=pool.invoke(new SummerRecursiveTask(collection));
                logger.log(Level.INFO,"current sum is " + sumVal+" in collection of size "+collection.size());
                Thread.yield();
           }

        }
    }
}
