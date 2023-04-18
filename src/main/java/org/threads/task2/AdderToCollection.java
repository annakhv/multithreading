package org.threads.task2;

import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdderToCollection implements Runnable{

    Collection<Integer> collection;

    private static Logger logger= Logger.getLogger(AdderToCollection.class.getName());

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
                    logger.log(Level.INFO,"element " + randomElement+  "is added to the collection of size "+collection.size());
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
