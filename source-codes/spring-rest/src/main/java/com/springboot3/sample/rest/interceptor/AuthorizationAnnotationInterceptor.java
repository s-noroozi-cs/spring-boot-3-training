package com.springboot3.sample.rest.interceptor;

import com.springboot3.sample.rest.annotation.Authorization;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AuthorizationAnnotationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            if (handlerMethod.hasMethodAnnotation(Authorization.class)) {
                checkAuthorization(request, handlerMethod.getMethodAnnotation(Authorization.class));
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void checkAuthorization(HttpServletRequest request, Authorization authorization) {

    }

}
