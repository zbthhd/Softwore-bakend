package com.example.management_platform.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = {RestController.class, Controller.class})//一个通知
@ResponseBody//把我们数据封装成json格式返回
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)//这儿表明要捕获哪种异常 这就是捕获所有的异常
    public R<String> ex(Exception exception){
        log.info(exception.getMessage());

        return R.error("操作失败 联系管理员");
    }
}
