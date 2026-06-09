package org.example;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        SchedulingStrategy strategy =
                new FIFOStrategy();

        CustomThreadPoolExecutor pool =
                new CustomThreadPoolExecutor(
                        2,
                        5,
                        100,
                        strategy);

        new Thread(
                new PoolMonitor(pool))
                .start();

        for(int i=1;i<=10;i++) {

            pool.submit(
                    new Task(i,1));
        }
        try {
            Thread.sleep(30000);
        }catch (Exception e){
            e.printStackTrace();
        }


        pool.shutdown();
    }
}