package com.itaddr.demo.oauth.domain;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;

@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T body;

    public ResultVO() {
    }

    public ResultVO(Integer code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public static ResultVO<Void> ok() {
        return new ResultVO<>(10001, "success", null);
    }

    public static <T> ResultVO<T> ok(T body) {
        return new ResultVO<T>(10001, "success", body);
    }

    public static ResultVO<Void> fail(int code, String msg) {
        return new ResultVO<>(code, msg, null);
    }

    @Setter
    public ResultVO<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Setter
    public ResultVO<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Setter
    public ResultVO<T> setBody(T body) {
        this.body = body;
        return this;
    }

}
