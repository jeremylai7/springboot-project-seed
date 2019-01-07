package com.springbootredis.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * @Auther: laizc
 * @Date: 2019/1/7 23:12
 * @Description: 角色
 */
@Data
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(generator = "UUID")
    private String roleId;

    private String roleName;

    private String authorityStr;

    private Date createDate;



}
