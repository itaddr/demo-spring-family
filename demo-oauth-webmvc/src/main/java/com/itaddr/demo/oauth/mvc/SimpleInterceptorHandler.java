package com.itaddr.demo.oauth.mvc;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Iterator;

public class SimpleInterceptorHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("===========================================");
        System.out.println("===AuthorizeInterceptorHandler.preHandle===");
        System.out.println(request.getMethod() + " " + request.getRequestURI());
        Enumeration<String> headerNames = request.getHeaderNames();
        for (; headerNames.hasMoreElements(); ) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + "=" + request.getHeader(headerName));
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println();
        System.out.println("===AuthorizeInterceptorHandler.postHandle===");
        System.out.println(response.getStatus());
        Iterator<String> headerNames = response.getHeaderNames().iterator();
        for (; headerNames.hasNext(); ) {
            String headerName = headerNames.next();
            System.out.println(headerName + "=" + response.getHeader(headerName));
        }
        System.out.println("===========================================");

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
