package com.itaddr.demo.simple.mvc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

public class TokenInterceptorHandler implements HandlerInterceptor {

    private static final String ACCESS_TOKEN = "access_token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class<?> clazz = handlerMethod.getBeanType();
        // 判断方法是否有返回值
        if (method.getReturnType() == void.class) {
            // 判断http content-type是否
            boolean rested = clazz.isAnnotationPresent(RestController.class) || clazz.isAnnotationPresent(ResponseBody.class) || method.isAnnotationPresent(ResponseBody.class);
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);


            }
        }

        String accessToken = request.getHeader(ACCESS_TOKEN);
        if (StringUtils.isBlank(accessToken)) {
            accessToken = request.getParameter(ACCESS_TOKEN);
        }
        if (StringUtils.isBlank(accessToken)) {
            // body content
            String bodyString = "{\"code\":10003,\"msg\":\"not found access_token\"}";
            // write header
            response.setStatus(200);
            response.addHeader("Vary", "Origin");
            response.addHeader("Content-Type", "application/json");
            response.addHeader("Date", new Date().toString());
            response.addHeader("Connection", "keep-alive");
            response.addHeader("Keep-Alive", "timeout=60");
            response.addHeader("Content-Length", String.valueOf(bodyString.length()));
            // write body
            response.getWriter().println(bodyString);
            response.flushBuffer();
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
