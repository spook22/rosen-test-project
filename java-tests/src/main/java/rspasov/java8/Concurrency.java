package rspasov.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Concurrency {

    private static String msg() {
        return "Executing task in thread: " + Thread.currentThread().getName();
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            System.out.println(msg());
            Runnable slowTask = () -> {
                System.out.println(msg());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            Thread newThread = new Thread(slowTask, "Custom Thread");
            newThread.start();

            executor.execute(slowTask);
            executor.submit(slowTask).get(); // Returns null as Runnable.run() returns void.

            System.out.println(executor.submit(() -> msg()).get());
            System.out.println(CompletableFuture.runAsync(slowTask, executor));
            System.out.println(CompletableFuture.supplyAsync(() -> msg(), executor).join());
        } finally {
            executor.shutdown();
        }
    }

}
