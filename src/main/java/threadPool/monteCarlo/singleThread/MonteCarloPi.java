package threadPool.monteCarlo.singleThread;

import java.util.Random;
import java.util.Scanner;

class MonteCarloPi {
    public static final Random rand = new Random(0);

    public static boolean isInCircle(double x, double y) {
        return x * x + y * y <= 1;
    }

    private static double calculateRatio(int numPoints) {
        int numInCircle = 0;
        for (int i = 0; i < numPoints; i++) {
            if (isInCircle(rand.nextDouble(), rand.nextDouble())) {
                numInCircle++;
            }
        }
        return (double) numInCircle / numPoints;
    }

    public static void main(String[] args) {
        System.out.println("how many points?");
        int numPoints;
        try (Scanner scanner = new Scanner(System.in)) {
            numPoints = scanner.nextInt();
        }
        long nowBefore = System.currentTimeMillis();
        double ratio = calculateRatio(numPoints);
        double pi = ratio * 4;
        long timeTaken = System.currentTimeMillis() - nowBefore;
        System.out.println("Monte Carlo approximination of pi: " + pi);
        System.out.println("Time taken:" + timeTaken + " ms");
    }
}
