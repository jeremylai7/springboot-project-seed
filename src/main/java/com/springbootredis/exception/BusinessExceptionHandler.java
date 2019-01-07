package com.springbootredis.exception;

import com.springbootredis.model.Result;
import com.springbootredis.util.OutUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: laizc
 * @Date: Created in 16:01 2019-01-07
 * @desc: 业务异常处理器
 */
@ControllerAdvice
public class BusinessExceptionHandler {
    @ResponseBody
    //捕获特定异常异常
    @ExceptionHandler(BusinessException.class)
    public Result handle(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return OutUtil.error(businessException.getCode(), businessException.getMessage());
        }
        return OutUtil.getResult(ResponseCodes.FAIL, null);
    }
}
