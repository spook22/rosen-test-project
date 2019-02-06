package rspasov.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Concurrency {

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String msg() {
        return "Executing task in thread: " + Thread.currentThread().getName();
    }

    private static void print(Object msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) throws Exception {
        //        testExecutor();
        testChaining();
    }

    protected static void testExecutor() throws Exception {
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

    protected static void testChaining() {

        //        CompletableFuture.runAsync(() -> {
        //            print("runAsync " + msg());
        //            sleep(1000);
        //        })
        //                .thenApply(value -> {
        //                    print("thenApply (" + value + ") " + msg());
        //                    sleep(500);
        //                    return value;
        //                })
        //                .thenAccept(value -> {
        //                    print("thenAccept (" + value + ") " + msg());
        //                    sleep(500);
        //                })
        //                .thenRun(() -> {
        //                    print("thenRun " + msg());
        //                    sleep(500);
        //                }).join();

        //        CompletableFuture.supplyAsync(() -> {
        //            print("supplyAsync " + msg());
        //            sleep(1000);
        //            return msg();
        //        })
        //                .thenApply(value -> {
        //                    print("thenApply (" + value + ") " + msg());
        //                    return value;
        //                })
        //                .thenAccept(value -> {
        //                    print("thenAccept (" + value + ") " + msg());
        //                })
        //                .thenRun(() -> {
        //                    print("thenRun " + msg());
        //                }).join();

        CompletableFuture<?> future = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            print("supplyAsync " + msg());
            return "supplyAsync";
        });

        //        future.thenCompose(value -> {
        //            print("thenCompose (" + value + ") " + msg());
        //            return CompletableFuture.supplyAsync(() -> {
        //                sleep(3000);
        //                print("thenCompose CF (" + value + ") " + msg());
        //                return "value";
        //            });
        //        });
        future.thenAccept(value -> {
            sleep(2000);
            print("thenAcceptAsync (" + value + ") " + msg());
        }).thenRunAsync(() -> {
            sleep(3000);
            print("thenRunAsync " + msg());
        }).whenCompleteAsync((result, exc) -> {
            sleep(2000);
            print("whenComplete " + result + msg());
        });


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
