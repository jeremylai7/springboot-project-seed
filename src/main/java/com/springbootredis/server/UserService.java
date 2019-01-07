package com.springbootredis.server;

import com.springbootredis.model.User;

import java.util.List;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 15:04
 * @Description:
 */
public interface UserService{
    public List<User> find();

    public User findById(User user);

    public int add(User user);

    public int update(User user);

    public int delete(int id);
}
