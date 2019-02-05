package rspasov.java8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Concurrency {

    public static void main(String[] args) throws Exception {
        Runnable task = () -> {
            System.out.println("Executing task in thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<?> future = executor.submit(task);
        System.out.println("Result is: " + future.get());

        executor.shutdown();
    }

}
