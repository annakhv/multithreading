package org.threads.task1;

import java.util.Map;

public class ValueSummer implements Runnable {

    Map<Integer, Integer> map;

    public ValueSummer(Map<Integer, Integer> map) {
        this.map = map;
    }

    public int calculateSum() {
        return map.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public void run() {
        while (true) {
            try {
                var sumVal = calculateSum();
                System.out.println("current sum is " + sumVal);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
