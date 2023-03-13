package com.springboot3.sample.rest.filter;

import com.springboot3.sample.rest.config.ApiKeys;
import com.springboot3.sample.rest.config.AttributeNames;
import com.springboot3.sample.rest.config.HeaderNames;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
@Slf4j
public class AuthenticationFilter implements Filter {

    private String[] ignoreUrls;

    public AuthenticationFilter(@Value("${spring.security.auth.ignore.urls}")
                                String[] ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }

    private boolean checkIgnoreUrls(HttpServletRequest httpRequest) {
        String currentRequestUri = httpRequest.getRequestURI();
        boolean ignoreThisRequest = false;
        for (String uri : ignoreUrls) {
            if (currentRequestUri.matches(uri)) {
                log.info("based on system configuration [ignore urls: " + ignoreUrls + "]" +
                        ",ignore authentication for this request url: " + currentRequestUri);
                return true;
            }
        }
        return false;
    }

    private boolean checkApiToken(HttpServletRequest httpRequest) {
        var apiKey = httpRequest.getHeader(HeaderNames.AUTHORIZATION);

        if (apiKey != null && ApiKeys.roleMapping.containsKey(apiKey)) {

            httpRequest.setAttribute(AttributeNames.CURRENT_USER_ROLES,
                    ApiKeys.roleMapping.get(apiKey));
            return true;
        }

        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;

        if (checkIgnoreUrls(httpRequest) || checkApiToken(httpRequest)) {

            filterChain.doFilter(servletRequest, servletResponse);

        } else {

            log.error("There is not exist any api-key in authorization header.");
            httpResponse.setStatus(401);
            httpResponse.getOutputStream().write("UN_AUTHORIZED".getBytes());

        }
    }
}