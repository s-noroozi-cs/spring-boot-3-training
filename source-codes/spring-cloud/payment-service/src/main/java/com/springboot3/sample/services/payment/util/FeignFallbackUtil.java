package com.springboot3.sample.services.payment.util;

import com.springboot3.sample.services.payment.service.CustomerClient;
import com.springboot3.sample.services.payment.service.MerchantClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import lombok.SneakyThrows;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.util.function.Supplier;

import static com.springboot3.sample.services.common.config.CustomHttpHeaders.RESPONSE_SOURCE;

public class FeignFallbackUtil {
    public static <T> T call(Supplier<T> main, Supplier<T> fallback) {
        try {
            return main.get();
        } catch (Throwable ex) {
            return fallback.get();
        }
    }

    public static CustomerClient makeCustomerProxy(CustomerClient primary, CustomerClient fallback) {
        return (CustomerClient) Proxy
                .newProxyInstance(CustomerClient.class.getClassLoader(),
                        new Class[]{CustomerClient.class},
                        new FallbackProxyPattern(primary, fallback));
    }

    @SneakyThrows
    private static <T> T makeFallback(Class<T> clazz) {
        return (T) clazz
                .getAnnotation(FeignClient.class)
                .fallback()
                .getDeclaredConstructor()
                .newInstance();

    }

    public static CustomerClient makeCustomerProxy(CustomerClient primary) {
        return makeCustomerProxy(primary,
                makeFallback(CustomerClient.class));
    }

    public static <T> T makeProxy(Class<T> clazz, T primary) {
        return (T) Proxy
                .newProxyInstance(clazz.getClassLoader(),
                        new Class[]{clazz},
                        new FallbackProxyPattern(primary, makeFallback(clazz)));
    }

    public static <T> T makeProxyWithCircuitBreaker(Class<T> clazz, T primary) {
        return (T) Proxy
                .newProxyInstance(clazz.getClassLoader(),
                        new Class[]{clazz},
                        new CircuitBreakerProxyPattern(primary, makeFallback(clazz)));
    }

    public static MerchantClient makeMerchantProxy(MerchantClient primary, MerchantClient fallback) {
        return (MerchantClient) Proxy
                .newProxyInstance(MerchantClient.class.getClassLoader(),
                        new Class[]{MerchantClient.class},
                        new FallbackProxyPattern(primary, fallback));
    }

    public static MerchantClient makeMerchantProxy(MerchantClient primary) {
        return makeMerchantProxy(primary,
                makeFallback(MerchantClient.class));
    }

    private record FallbackProxyPattern<T>
            (T primaryService, T fallbackService) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                Object result = method.invoke(primaryService, args);
                if (result != null) return result;
            } catch (Throwable ignore) {
                //ignore exception
            }
            return method.invoke(fallbackService, args);
        }
    }

    private record CircuitBreakerProxyPattern<T>
            (T primaryService, T fallbackService) implements InvocationHandler {

        private static CircuitBreaker cb;

        private CircuitBreaker getCircuitBreaker() {
            if (cb == null) {
                synchronized (this) {
                    var name = "test";
                    var cfg = CircuitBreakerConfig
                            .custom()
                            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                            .slidingWindowSize(2)
                            .minimumNumberOfCalls(2)
                            .failureRateThreshold(50.0f)
                            .waitDurationInOpenState(Duration.ofSeconds(1000))
                            .recordResult(o -> {
                                if (o instanceof ResponseEntity re) {
                                    return re.getHeaders()
                                            .containsKey(RESPONSE_SOURCE)
                                            &&
                                            "fallback".equals(
                                                    re.getHeaders()
                                                            .get(RESPONSE_SOURCE)
                                                            .get(0));
                                }
                                return false;
                            })
                            .automaticTransitionFromOpenToHalfOpenEnabled(false)
                            .permittedNumberOfCallsInHalfOpenState(1)
                            .build();
                    cb = CircuitBreaker.of(name, cfg);
                }
            }
            return cb;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            Supplier<Object> myMethod = () -> {
                try {
                    Object result = method.invoke(primaryService, args);
                    if (result != null) return result;
                } catch (Throwable ignore) {
                    //ignore exception
                }
                try {
                    return method.invoke(fallbackService, args);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            };

            return getCircuitBreaker().decorateSupplier(myMethod).get();
        }
    }

}
