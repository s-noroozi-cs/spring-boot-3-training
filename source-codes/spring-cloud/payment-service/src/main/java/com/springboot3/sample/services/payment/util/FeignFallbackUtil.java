package com.springboot3.sample.services.payment.util;

import io.vavr.control.Try;

import java.util.function.Supplier;

public class FeignFallbackUtil {
    public static <T> T call(Supplier<T> main, Supplier<T> fallback) {
        return Try.of(() -> main.get())
                .fold(ex -> fallback.get(), i -> i);
    }
}
