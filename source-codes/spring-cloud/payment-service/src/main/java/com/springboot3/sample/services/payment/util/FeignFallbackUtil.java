package com.springboot3.sample.services.payment.util;

import java.util.function.Supplier;

public class FeignFallbackUtil {
    public static <T> T call(Supplier<T> main, Supplier<T> fallback) {
        try {
            return main.get();
        } catch (Throwable ex) {
            return fallback.get();
        }
    }
}
