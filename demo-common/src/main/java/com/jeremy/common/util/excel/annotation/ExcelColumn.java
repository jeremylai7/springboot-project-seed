package com.jeremy.common.util.excel.annotation;

import java.lang.annotation.*;

/**
 * @author Austen
 * @create 2018-03-30 15:22
 * @desc EXCEL列注释
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {

    public String value() default "";
    
}
