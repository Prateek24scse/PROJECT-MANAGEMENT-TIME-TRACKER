package com.timetracker.service;

import javafx.application.Platform;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * AsyncDataLoader class demonstrating:
 * - Multithreading (ExecutorService, Future, Callable)
 * - Synchronization (synchronized blocks, CompletableFuture)
 * - Generics in collections and methods
 */
public class AsyncDataLoader {
    
    private final ExecutorService executorService;
    private final ConcurrentHashMap<String, Future<?>> runningTasks = new ConcurrentHashMap<>();
    
    public AsyncDataLoader() {
        // Create a thread pool for async operations
        this.executorService = Executors.newFixedThreadPool(5);
    }
    
    /**
     * Loads data asynchronously
     * @param dataLoader The function that loads data
     * @param onSuccess Callback for successful load
     * @param onError Callback for errors
     * @param <T> The type of data to load
     */
    public <T> void loadAsync(Callable<T> dataLoader, Consumer<T> onSuccess, Consumer<Exception> onError) {
        String taskId = Thread.currentThread().getName() + "-" + System.currentTimeMillis();
        
        Future<T> future = executorService.submit(() -> {
            try {
                T result = dataLoader.call();
                Platform.runLater(() -> onSuccess.accept(result));
                return result;
            } catch (Exception e) {
                Platform.runLater(() -> onError.accept(e));
                throw new RuntimeException(e);
            }
        });
        
        runningTasks.put(taskId, future);
    }
    
    /**
     * Loads data with timeout
     * @param dataLoader The function that loads data
     * @param timeout Timeout in seconds
     * @param onSuccess Callback for successful load
     * @param onError Callback for errors
     * @param <T> The type of data to load
     */
    public <T> void loadAsyncWithTimeout(Callable<T> dataLoader, int timeout, 
                                          Consumer<T> onSuccess, Consumer<Exception> onError) {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> {
            try {
                return dataLoader.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, executorService);
        
        future.orTimeout(timeout, TimeUnit.SECONDS)
            .thenAccept(result -> Platform.runLater(() -> onSuccess.accept(result)))
            .exceptionally(throwable -> {
                Platform.runLater(() -> onError.accept((Exception) throwable));
                return null;
            });
    }
    
    /**
     * Cancels a running task
     * @param taskId The task ID
     */
    public synchronized void cancelTask(String taskId) {
        Future<?> future = runningTasks.get(taskId);
        if (future != null && !future.isDone()) {
            future.cancel(true);
            runningTasks.remove(taskId);
        }
    }
    
    /**
     * Shuts down the executor service
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

