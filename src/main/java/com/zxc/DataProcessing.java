package com.zxc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DataProcessing {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> source1 = CompletableFuture.supplyAsync(() -> fetchData("Source 1"));
        CompletableFuture<String> source2 = CompletableFuture.supplyAsync(() -> fetchData("Source 2"));
        CompletableFuture<String> source3 = CompletableFuture.supplyAsync(() -> fetchData("Source 3"));

        CompletableFuture<Void> allTasks = CompletableFuture.allOf(source1, source2, source3);

        CompletableFuture<String> combinedResult = allTasks.thenApply(v -> {
            try {
                return source1.get() + ", " + source2.get() + ", " + source3.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Combined data: " + combinedResult.get());
    }

    private static String fetchData(String sourceName) {
        try {
            Thread.sleep((long) (Math.random() * 2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return sourceName + " data";
    }
}
