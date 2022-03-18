package pl.pjwstk.s17651.mvcdemo.utils;

public class TimeParser {

    public static double getDurationInSeconds(long startTime) {
        return (double)(System.nanoTime() - startTime) / 1000000000;
    }
}
