package org.threads.task2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SquareRootSumRecursiveTask extends RecursiveTask<Double> {

    private static Logger logger= Logger.getLogger(SquareRootSumRecursiveTask.class.getName());
    private Collection<Integer> collection;

    public SquareRootSumRecursiveTask(Collection<Integer> collection) {
        this.collection = collection;
    }

    private static final int threshold = 30;

    @Override
    protected Double compute() {
        if (collection.size() > threshold) {
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream()
                    .mapToDouble(ForkJoinTask::join)
                    .sum();

        } else {
            return processing(collection);
        }
    }

    private Collection<SquareRootSumRecursiveTask> createSubtasks() {
        Collection<SquareRootSumRecursiveTask> adderTasks = new ArrayList<>();
        Object[] array = collection.toArray();
        Collection<Integer> coll1 = Arrays.stream(Arrays.copyOfRange(array, 0, array.length / 2))
                .map(obj -> (Integer) obj)
                .toList();
        Collection<Integer> coll2 = Arrays.stream(Arrays.copyOfRange(array, array.length / 2, array.length))
                .map(obj -> (Integer) obj)
                .toList();
        adderTasks.add(new SquareRootSumRecursiveTask(coll1));
        adderTasks.add(new SquareRootSumRecursiveTask(coll2));
        return adderTasks;

    }

    private double processing(Collection<Integer> collection) {
        logger.log(Level.INFO,Thread.currentThread().getName() +" is processing SquareRootSumRecursiveTask");
        return collection.stream()
                .map(number -> Math.pow(number, 2))
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
