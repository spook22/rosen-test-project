package rspasov.java8;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

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

            Callable<String> logMessageTask = () -> msg();
            Future<String> future = executor.submit(logMessageTask);
            System.out.println(future.get());

            CompletableFuture<Void> voidFuture = CompletableFuture.runAsync(slowTask, executor);
            System.out.println(voidFuture.get());
            // CompletableFuture.runAsync(logMessageTask, executor); // Does not compile.

            Supplier<String> logMessageSupplier = () -> msg();
            CompletableFuture<String> cFuture = CompletableFuture.supplyAsync(logMessageSupplier,
                    executor);
            System.out.println(cFuture.get());

        } finally {
            executor.shutdown();
        }
    }

}
