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
        var valueSummer = new ValueSummer(map);
        Thread threadSummer = new Thread(valueSummer);
        threadAdder.start();  //this one gives concurentmod exception
        threadSummer.start();//this one gives concurentmod exception
        ///////////////////example 2
        Map<Integer, Integer> threadSafeMap = new ConcurrentHashMap<>();
        var adderThreadSafe = new Adder(threadSafeMap);
        Thread threadSafeAdder = new Thread(adderThreadSafe);
        var valueSummerThreadSafe = new ValueSummer(threadSafeMap);
        Thread threadSafeSummer = new Thread(valueSummerThreadSafe);
      //  threadSafeAdder.start();  // this one does not give concurent mod exception because it uses syncronised implementation
       //  threadSafeSummer.start(); // same as above
    }
}
