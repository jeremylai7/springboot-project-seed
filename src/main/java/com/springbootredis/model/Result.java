package com.springbootredis.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: laizc
 * @Date: Created in 14:36 2019-01-07
 * @desc 统一返回对象
 */
@Getter
@Setter
public class Result {

    private String code;

    private String message;

    private Object data;

}
