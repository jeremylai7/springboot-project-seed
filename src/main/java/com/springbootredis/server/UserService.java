package com.springbootredis.server;

import com.springbootredis.model.User;
import com.springbootredis.model.enums.UserType;

import java.util.List;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 15:04
 * @Description:
 */
public interface UserService{
    List<User> findAll(UserType state);

    List<User> find();

    User findById(User user);

    int add(User user);

    int update(User user);

    int delete(int id);
}
