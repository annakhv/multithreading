package org.threads.task1;

import java.util.Map;
import java.util.Random;

public class Adder implements Runnable {

    Map<Integer, Integer> map;

    public Adder(Map<Integer, Integer> map) {
        this.map = map;
    }

    public void addElement(Integer key, Integer value) {
        map.put(key, value);
    }

    @Override
    public void run() {
        while (true) {
            try {
                var randomKey = new Random().nextInt();
                var randomValue = new Random().nextInt();
                addElement(randomKey, randomValue);
                System.out.println("element with key " + randomKey + " and with value " + randomValue + " is added to the map by thread "+Thread.currentThread().getName());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}