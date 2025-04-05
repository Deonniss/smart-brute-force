package ru.golovin.sbf.util;

public class TimerUtil {

    private long startTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        long elapsedTime = System.nanoTime() - startTime;
        System.out.printf("Elapsed time: %s ns%n", elapsedTime);
        System.out.printf("Elapsed time: %s ms%n", elapsedTime / 1_000_000);
        System.out.printf("Elapsed time: %s s%n", elapsedTime / 1_000_000_000);
    }
}
