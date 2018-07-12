package threadPool.monteCarlo.threadPool;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

class MonteCarloPi {
    public static void main(String[] args) {
        int numPoints;
        int numTasks;
        int numWorkers;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("How many points?");
            numPoints = scanner.nextInt();
            System.out.println("How many tasks?");
            numTasks = scanner.nextInt();
            System.out.println("How many workers?");
            numWorkers = scanner.nextInt();
        }

        int pointsPerTask = numPoints / numTasks;
        if (pointsPerTask <= 0) {
            throw new IllegalArgumentException("Too few points for tasks");
        }
        int totalPoints = pointsPerTask * numTasks;
        PiResult results[] = new PiResult[numTasks];
        PiTask tasks[] = new PiTask[numTasks];
        for (int i = 0; i < numTasks; i++) {
            results[i] = new PiResult();
            tasks[i] = new PiTask(pointsPerTask, results[i]);
        }

        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);
        ((ThreadPoolExecutor) executor).prestartAllCoreThreads();

        @SuppressWarnings("unchecked")
        Future<PiResult> futures[] = new Future[numTasks];

        long nowBefore = System.currentTimeMillis();

        for (int i = 0; i < numTasks; i++) {
            futures[i] = executor.submit(tasks[i], results[i]);
        }
        for (int i = 0; i < numTasks; i++) {
            try {
                futures[i].get();
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }
        long timeTaken = System.currentTimeMillis() - nowBefore;
        int totalInCircle = 0;
        for (int i = 0; i < numTasks; i++) {
            totalInCircle += results[i].getResult();
        }
        double pi = (double) 4 * totalInCircle / totalPoints;
        System.out.println("Monte Carlo approximation of pi: " + pi);

        System.out.println("Time taken: " + timeTaken + " ms");

        executor.shutdownNow();
    }
}
