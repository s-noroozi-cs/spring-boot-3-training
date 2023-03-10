package com.springboot3.sample.rest.filter;

import com.springboot3.sample.rest.model.HttpLogModel;
import com.springboot3.sample.rest.util.HttpUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        var startTime = System.currentTimeMillis();
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;

        var builder = HttpLogModel.builder()
                .url(httpRequest.getRequestURL().toString())
                .uri(httpRequest.getRequestURI())
                .method(httpRequest.getMethod())
                .requestHeaders(HttpUtil.extractHeaders(httpRequest));
        try {

            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Throwable ex) {
            builder.exceptionMsg(ex.getMessage());
        } finally {

            builder
                    .elapseTime(System.currentTimeMillis() - startTime)
                    .statusCode(httpResponse.getStatus())
                    .responseHeaders(HttpUtil.extractHeaders(httpResponse));


            HttpLogModel httpLogModel = builder.build();
            if (httpLogModel.hasError())
                log.error(httpLogModel.asJsonString());
            else
                log.info(httpLogModel.asJsonString());
        }

    }
}
