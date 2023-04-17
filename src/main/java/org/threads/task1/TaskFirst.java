package org.threads.task1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskFirst implements Runnable {
    @Override
    public void run() {
        ////////////example 1
        Map<Integer, Integer> map = new HashMap<>();
        var adder = new Adder(map);
        Thread threadAdder = new Thread(adder);
        threadAdder.setName("Adder thread not threadsafe");
        var valueSummer = new ValueSummer(map);
        Thread threadSummer = new Thread(valueSummer);
        threadSummer.setName("value summer thread not threadsafe");
        threadAdder.start();  //this one gives concurentmod exception
        threadSummer.start();//this one gives concurentmod exception
        ///////////////////example 2
        Map<Integer, Integer> threadSafeMap = new ConcurrentHashMap<>();
        var adderThreadSafe = new Adder(threadSafeMap);
        Thread threadSafeAdder = new Thread(adderThreadSafe);
        threadSafeAdder.setName("adder thread safe");
        var valueSummerThreadSafe = new ValueSummer(threadSafeMap);
        Thread threadSafeSummer = new Thread(valueSummerThreadSafe);
        threadSafeSummer.setName("val summer thread safe");
        threadSafeAdder.start();  // this one does not give concurent mod exception because it uses syncronised implementation
        threadSafeSummer.start(); // same as above
    }


    public static void main(String[] args) {
        Thread threadTask1=new Thread(new TaskFirst());
        threadTask1.start();
    }
}
