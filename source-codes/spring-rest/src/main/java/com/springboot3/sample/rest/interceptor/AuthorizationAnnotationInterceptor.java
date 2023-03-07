package com.springboot3.sample.rest.interceptor;

import com.springboot3.sample.rest.annotation.Authorization;
import com.springboot3.sample.rest.config.AttributeNames;
import com.springboot3.sample.rest.exception.ForbiddenException;
import com.springboot3.sample.rest.model.RoleNames;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

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
        List<RoleNames> roles = (List<RoleNames>)
                request.getAttribute(AttributeNames.CURRENT_USER_ROLES);

        if (roles == null || !roles.containsAll(Arrays.asList(authorization.value())))
            throw new ForbiddenException("You have not authorize to do this action");
    }

}
