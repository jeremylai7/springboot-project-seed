package com.jeremy.data.user.model;

import com.jeremy.data.user.enums.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: laizc
 * @Date: Created in  2020-07-16
 * @desc:
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


