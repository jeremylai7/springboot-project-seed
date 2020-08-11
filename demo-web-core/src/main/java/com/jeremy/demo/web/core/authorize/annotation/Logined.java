package com.jeremy.demo.web.core.authorize.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: laizc
 * @Date: Created in 16:20 2019-01-04
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Logined {

    boolean isLogined() default true;
}
