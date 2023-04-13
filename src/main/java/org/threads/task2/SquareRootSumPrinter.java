package org.threads.task2;

import java.util.Collection;

public class SquareRootSumPrinter implements Runnable {
    private Collection<Integer> collection;

    public SquareRootSumPrinter(final Collection<Integer> collection) {
        this.collection = collection;
    }

    public double calculateSumOfSquareAquareRoot() {
        return Math.sqrt(collection.stream()
                                 .map(number -> Math.pow(number, 2))
                                 .mapToDouble(Double::doubleValue)
                                 .sum());
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (collection){
                    var sumVal = calculateSumOfSquareAquareRoot();
                    System.out.println("current square root of squared sum is " + sumVal);
                    Thread.sleep(50);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
