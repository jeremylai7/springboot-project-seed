package com.springbootredis.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: laizc
 * @Date: Created in 16:44 2018-12-18
 */
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增
    private Integer id;

    private String roleId;

    private String username;

    private String password;

    private Integer age;
    //是否是超级管理员
    private Boolean top;


}
