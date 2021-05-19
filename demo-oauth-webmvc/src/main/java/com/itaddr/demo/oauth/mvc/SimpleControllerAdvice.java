package com.itaddr.demo.oauth.mvc;

import com.itaddr.demo.oauth.domain.ResultVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class SimpleControllerAdvice implements ResponseBodyAdvice<Object> {

    /**
     * -------- 通用异常处理方法 --------
     **/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVO<Void> error(Exception e) {
        // 通用异常结果打印到日志
        System.out.println("捕获到异常Exception>>>");
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return ResultVO.fail(11001, writer.toString());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResultVO<Void> error(NullPointerException e) {
        System.out.println("捕获到异常NullPointerException>>>");
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return ResultVO.fail(11001, stringWriter.toString());
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
