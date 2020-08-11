package com.jeremy.demo.web.core.exception.handle;

import com.jeremy.demo.web.core.utils.OutUtil;
import com.jeremy.demo.web.core.view.Result;
import com.jeremy.service.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @Author: laizc
 * @Date: Created in 10:23 2020-03-02
 * @desc: 全局异常捕获
 */
@RestControllerAdvice
public class GlobalExceptionHandle {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    /**
     * 业务异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result businessHandle(BusinessException e){
        Result result =  OutUtil.error(e.getCode(),e.getMessage());
        log.error("business exception" + e.getMessage());
        return result;
    }

    /**
     * 对象验证异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Result bindHandle(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        Result result = OutUtil.error("400",message);
        log.error("bind exception" + e.getMessage());
        return result;
    }

    /**
     * 参数验证异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result constraintHandle(ConstraintViolationException e){
        Set<ConstraintViolation<?>> constraintViolationSet = e.getConstraintViolations();
        String message = "";
        for (ConstraintViolation constraintViolation : constraintViolationSet){
            message = constraintViolation.getMessage();
            break;
        }
        Result result = OutUtil.error("400",message);
        log.error("ConstraintViolation exception" + e.getMessage());
        return result;
    }

}
