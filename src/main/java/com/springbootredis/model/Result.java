package com.springbootredis.model;

import lombok.Data;

/**
 * @Author: laizc
 * @Date: Created in 14:36 2019-01-07
 * @desc 统一返回对象
 */
@Data
public class Result {
    private String code;

    private String message;

    private Object data;

}
