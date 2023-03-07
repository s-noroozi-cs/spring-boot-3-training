package com.springboot3.sample.rest.filter;

import com.springboot3.sample.rest.config.ApiKeys;
import com.springboot3.sample.rest.config.AttributeNames;
import com.springboot3.sample.rest.config.HeaderNames;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Order(2)
@Slf4j
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;

        var apiKey = Optional.ofNullable(httpRequest.getHeader(HeaderNames.AUTHORIZATION));

        if (apiKey.isPresent() && ApiKeys.roleMapping.containsKey(apiKey)) {

            httpRequest.setAttribute(AttributeNames.CURRENT_USER_ROLES,
                    ApiKeys.roleMapping.get(apiKey));

            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            log.error("There is not exist any api-key in authorization header.");
            httpResponse.setStatus(401);
            httpResponse.getOutputStream().write("UN_AUTHORIZED".getBytes());
        }
    }
}
