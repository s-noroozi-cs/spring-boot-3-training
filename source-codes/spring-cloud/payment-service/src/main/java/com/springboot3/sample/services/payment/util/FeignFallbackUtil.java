package com.springboot3.sample.services.payment.util;

import com.springboot3.sample.services.payment.service.CustomerClient;
import com.springboot3.sample.services.payment.service.MerchantClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Supplier;

public class FeignFallbackUtil {
    public static <T> T call(Supplier<T> main, Supplier<T> fallback) {
        try {
            return main.get();
        } catch (Throwable ex) {
            return fallback.get();
        }
    }

    public static CustomerClient makeCustomerProxy(CustomerClient primary, CustomerClient fallback) {
        return (CustomerClient) Proxy.newProxyInstance(
                CustomerClient.class.getClassLoader(),
                new Class[]{CustomerClient.class},
                new FallbackProxyPattern(primary, fallback)
        );
    }

    public static MerchantClient makeMerchantProxy(MerchantClient primary, MerchantClient fallback) {
        return (MerchantClient) Proxy.newProxyInstance(
                MerchantClient.class.getClassLoader(),
                new Class[]{MerchantClient.class},
                new FallbackProxyPattern(primary, fallback)
        );
    }

    private record FallbackProxyPattern<T>
            (T primaryService, T fallbackService)
            implements InvocationHandler {

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

}
