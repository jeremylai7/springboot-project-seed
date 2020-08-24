package com.jeremy.service.user;


import com.jeremy.data.query.PageQuery;
import com.jeremy.data.user.model.User;
import com.jeremy.service.base.BaseService;
import com.jeremy.service.exception.BusinessException;

import java.util.List;

/**
 * @Author: laizc
 * @Date: Created in 9:52 2019-06-25
 */
public interface IndexServer extends BaseService<User> {
    /**
     * 添加
     * @param
     */
    void add(String username, String password) throws BusinessException;

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
     * 分页查询 整合redis
     * @param query
     * @return
     */
    List<User> findRedis(PageQuery query);

    /**
     * 清理缓存
     */
    void ClearnCache();

    void transTest(String aa) throws Exception;
}
