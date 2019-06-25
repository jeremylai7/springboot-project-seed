package com.springbootredis.server;
import com.springbootredis.exception.BusinessException;
import com.springbootredis.model.User;

/**
 * @Author: laizc
 * @Date: Created in 9:52 2019-06-25
 */
public interface IndexServer {
    /**
     * 添加
     * @param
     */
    void add(String username,String password) throws BusinessException;

    /**
     * 修改 all
     * @param user
     */
    void updateAll(User user) throws BusinessException;

}
