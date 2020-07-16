package com.springbootredis.service;

import com.springbootredis.exception.BusinessException;
import com.springbootredis.model.User;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 15:04
 * @Description:
 */
public interface UserService extends BaseService<User>{

    /**
     * 登录
     * @param username  用户名
     * @param password  密码
     * @return
     */
    User login(String username, String password) throws BusinessException;
}
