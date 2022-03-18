package pl.pjwstk.s17651.reactivedemo.utils;

import java.util.function.Supplier;

public class MatchIdGenerator {

    private static Long matchId = 1L;

    private static Supplier<Long> matchIdSupplier = () -> matchId++;

    public static Long generateMatchNextId() {
        return matchIdSupplier.get();
    }
}
