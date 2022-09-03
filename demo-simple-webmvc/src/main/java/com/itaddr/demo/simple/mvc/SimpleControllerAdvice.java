package com.itaddr.demo.simple.mvc;

import com.itaddr.demo.simple.domain.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@ControllerAdvice
public class SimpleControllerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * -------- 通用异常处理方法 --------
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVO<Void> error(Exception e, HttpServletRequest request) {
        // 通用异常结果打印到日志
        System.out.println("捕获到异常Exception>>>");
        e.printStackTrace();

        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return ResultVO.fail(11001, writer.toString());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResultVO<Void> error(NullPointerException e, HttpServletRequest request) {
        System.out.println("捕获到异常NullPointerException>>>");
        e.printStackTrace();

        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return ResultVO.fail(11001, stringWriter.toString());
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class) //捕获特定异常
    @ResponseStatus(HttpStatus.NOT_MODIFIED) // 返回304状态码
    @ResponseBody
    public void handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e, HttpServletRequest request) {
        e.printStackTrace();
        System.out.println("handleAsyncRequestTimeoutException");
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {

        return false;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return o;
    }

}
