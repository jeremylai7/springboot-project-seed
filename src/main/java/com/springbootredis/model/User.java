package com.springbootredis.model;

import com.springbootredis.config.handle.EnumHandle;
import com.springbootredis.model.enums.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.*;

/**
 * @Author: laizc
 * @Date: Created in 16:44 2018-12-18
 */
@ApiModel("用户信息")
@Table(name = "t_user")
@Data
public class User {
    @ApiModelProperty("主键")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增
    private Integer id;

    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    private Integer age;
    //是否是超级管理员
    private Boolean top;

    @ApiModelProperty("用户类型 NORMAL:正常,APPRVL:认证,STOP:禁用")
    private UserType userType;


}
