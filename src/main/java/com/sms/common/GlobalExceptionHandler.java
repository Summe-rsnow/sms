package com.sms.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * 全局异常处理，根据注解来拦截
 *
 * @author sssnow
 */
//@ControllerAdvice(annotations = {RestController.class, Controller.class})
//@ResponseBody
//@Slf4j
public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)//处理异常注解
//    public Result<String> exceptionHandler(SQLException ex) {
//        String message = ex.getMessage();
//        log.error(message);
//        return Result.error("请联系管理员解决问题,错误信息:" + message);
//    }
}
