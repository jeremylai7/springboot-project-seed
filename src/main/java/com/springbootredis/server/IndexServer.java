package com.springbootredis.server;
import com.springbootredis.exception.BusinessException;
import com.springbootredis.model.User;
import com.springbootredis.model.UserQuery;

import java.util.List;

/**
 * @Author: laizc
 * @Date: Created in 9:52 2019-06-25
 */
public interface IndexServer extends BaseService<User>{
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

    /**
     * 修改
     * @param user
     */
    void update(User user) throws BusinessException;


    /**
     * 分页查询
     * @param query
     * @return
     */
    List<User> find(UserQuery query);

    /**
     * 分页查询 整合redis
     * @param query
     * @return
     */
    List<User> findRedis(UserQuery query);

    /**
     * 清理缓存
     */
    void ClearnCache();
}
