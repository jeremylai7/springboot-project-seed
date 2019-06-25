package com.springbootredis.dao;

import com.springbootredis.model.User;
import com.springbootredis.model.enums.UserType;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: laizc
 * @Date: Created in 10:08 2019-01-07
 */
public interface UserDao extends Mapper<User>{

}
