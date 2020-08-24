package com.jeremy.service.annotation;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * @Author: laizc
 * @Date: Created in  2020-08-24
 * @desc:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional(rollbackFor = Throwable.class,isolation = Isolation.READ_COMMITTED)
public @interface WriteTransactional {
}
