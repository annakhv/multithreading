package org.threads.task1;

import java.util.Map;

public class ValueSummer implements Runnable {

    Map<Integer, Integer> map;

    public ValueSummer(Map<Integer, Integer> map) {
        this.map = map;
    }

    public int calculateSum() {
        return map.values()
                .parallelStream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public void run() {
        while (true) {
            try {
                var sumVal = calculateSum();
                System.out.println("current sum is " + sumVal +" calculated by thread "+Thread.currentThread().getName());
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
