package com.caseyjofleck.simplegrocerylist.config;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException {
        MDC.put("requestPath", request.getRequestURI());
        MDC.put("requestMethod", request.getMethod());
        MDC.put("sessionId", request.getSession().getId()); //unique number server assigns to user for the duration of their visit
        filterChain.doFilter(request, response);
        MDC.clear();
    }
}
