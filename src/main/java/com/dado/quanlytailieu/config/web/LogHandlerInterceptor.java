package com.dado.quanlytailieu.config.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;


@Component
@Slf4j
public class LogHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (notNeedLog(handler)) {
            return true;
        }
        log.debug("-----> {} {}", request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, @Nullable Exception ex) {
        if (notNeedLog(handler)) {
            return;
        }
        if (ex == null) {
            log.debug("<----- {} {}", request.getMethod(), request.getRequestURI());
        } else {
            log.warn("<----- {} {} error {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
        }
    }

    private static boolean notNeedLog(Object handler) {
        return handler instanceof ResourceHttpRequestHandler;
    }
}
