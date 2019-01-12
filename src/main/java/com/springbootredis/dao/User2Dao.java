package com.springbootredis.dao;

import com.springbootredis.model.User;
import com.springbootredis.model.enums.UserType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: laizc
 * @Date: 2019/1/12 20:21
 * @Description:
 */

public interface User2Dao {
    public List<User> findByType(@Param("state") UserType state);

    public int add(User user);
}
