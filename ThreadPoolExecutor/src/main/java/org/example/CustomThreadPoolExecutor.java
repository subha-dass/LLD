package org.example;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadPoolExecutor {
    private int corePoolSize;

    private int maxPoolSize;

    private BlockingQueue<Task> queue;

    private final List<Worker> workers =
            new CopyOnWriteArrayList<>();

    private final AtomicInteger activeWorkers =
            new AtomicInteger();

    public CustomThreadPoolExecutor(
    int corePoolSize,
    int maxPoolSize,
    int capacity,
    SchedulingStrategy schedulingStrategy) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.queue=schedulingStrategy.createQueue(capacity);
        initializeWorkers();
    }

    private void initializeWorkers() {

        for(int i=0;i<corePoolSize;i++) {

            addWorker();
        }
    }

    private synchronized
    void addWorker() {

        Worker worker =
                new Worker(queue);

        Thread thread =
                new Thread(worker);

        workers.add(worker);

        activeWorkers.incrementAndGet();

        System.out.println(
                "Worker Added : "
                        + activeWorkers.get());

        thread.start();
    }


    public void submit(Task task) {

        if(!queue.offer(task)) {

            if(activeWorkers.get()
                    < maxPoolSize) {

                addWorker();

                queue.offer(task);

            } else {

                throw new RuntimeException(
                        "Task Rejected");
            }
        }

        //scaleUpIfRequired();
    }

    private synchronized
    void scaleUpIfRequired() {

        int queueSize = queue.size();
        int capacity = queue.remainingCapacity() + queueSize;

        if (capacity > 0) {
            double loadFactor = (double) queueSize / capacity;

            if (loadFactor > 0.75 && activeWorkers.get() < maxPoolSize) {
                addWorker();
            }
        }
    }

    public synchronized
    void scaleDown() {

        if(queue.isEmpty()
                && activeWorkers.get()
                > corePoolSize) {

            Worker worker =
                    workers.remove(
                            workers.size()-1);

            worker.stopWorker();

            activeWorkers.decrementAndGet();

            System.out.println(
                    "Worker Removed : "
                            + activeWorkers.get());
        }
    }



    public void shutdown() {

        for(Worker worker : workers) {

            worker.stopWorker();
        }
    }

}
