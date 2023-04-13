package org.threads.task2;

import java.util.Collection;
import java.util.Random;

public class AdderToCollection implements Runnable{

    Collection<Integer> collection;

    public AdderToCollection(final Collection<Integer> collection) {
        this.collection = collection;
    }


    public void add(Integer number){
        collection.add(number);
    }
    @Override
    public void run() {
        while (true) {
            try {
                var randomElement = new Random().nextInt();
                synchronized (collection){
                    add(randomElement);
                    System.out.println("element " + randomElement+  "is added to the collection");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
