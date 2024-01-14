package com.sms.common;

import com.sms.enums.ResultCodeEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sssnow
 */
@Data
public class Result<T> implements Serializable {

    private Integer code;

    private String msg; //信息

    private T data; //数据


    private Result() {
    }

    public static <T> Result<T> success(String msg, T object) {
        Result<T> result = new Result<>();
        result.data = object;
        result.msg = msg;
        result.code = ResultCodeEnums.SUCCESS.getCode();
        return result;
    }

    public static <T> Result<T> success(String msg) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = ResultCodeEnums.SUCCESS.getCode();
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.data = object;
        result.code = ResultCodeEnums.SUCCESS.getCode();
        return result;
    }

    public static Result<String> success() {
        Result<String> result = new Result<>();
        result.code = ResultCodeEnums.SUCCESS.getCode();
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = ResultCodeEnums.FAIL.getCode();
        return result;
    }
}
