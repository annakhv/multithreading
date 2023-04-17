package org.threads.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SummerRecursiveTask extends RecursiveTask<Integer> {
    private static Logger logger= Logger.getLogger(SummerRecursiveTask.class.getName());
    private Collection<Integer> collection;

    public SummerRecursiveTask(Collection<Integer> collection) {
        this.collection = collection;
    }

    private static final int threshold = 30;

    @Override
    protected Integer compute() {
        if (collection.size() > threshold) {
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();

        } else {
            return processing(collection);
        }
    }

    private Collection<SummerRecursiveTask> createSubtasks() {
        Collection<SummerRecursiveTask> adderTasks = new ArrayList<>();
        Object[] array = collection.toArray();
        Collection<Integer> coll1 = Arrays.stream(Arrays.copyOfRange(array, 0, array.length / 2))
                .map(obj -> (Integer) obj)
                .toList();
        Collection<Integer> coll2 = Arrays.stream(Arrays.copyOfRange(array, array.length / 2, array.length))
                .map(obj -> (Integer) obj)
                .toList();
        adderTasks.add(new SummerRecursiveTask(coll1));
        adderTasks.add(new SummerRecursiveTask(coll2));
        return adderTasks;

    }

    private Integer processing(Collection<Integer> coll) {
        logger.log(Level.INFO,Thread.currentThread().getName() +" is processing summerRecursiveTask");
        return coll
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
