package org.threads.task2;

import java.util.ArrayList;
import java.util.Collection;

public class TaskSecond implements Runnable{
    @Override
    public void run() {
        Collection<Integer> collection = new ArrayList<>();
        var adder=new AdderToCollection(collection);
        Thread adderThread=new Thread(adder);
        var squareRootSumPrinter=new SquareRootSumPrinter(collection);
        Thread sqrtPrinterThread=new Thread(squareRootSumPrinter);
        var sumPrinter=new SumPrinter(collection);
        Thread sumPrinterThread=new Thread(sumPrinter);
        adderThread.start();
        sqrtPrinterThread.start();
        sumPrinterThread.start();

    }
}
