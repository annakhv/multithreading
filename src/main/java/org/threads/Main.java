package org.threads;

import org.threads.task1.TaskFirst;
import org.threads.task2.TaskSecond;

public class Main {
    public static void main(String[] args) {
       // Thread threadTask1=new Thread(new TaskFirst());
       // threadTask1.start();
        Thread threadTask2=new Thread(new TaskSecond());
        threadTask2.start();
    }

}