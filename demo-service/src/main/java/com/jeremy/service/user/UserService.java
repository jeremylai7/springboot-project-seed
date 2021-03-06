package com.jeremy.service.user;

import com.jeremy.data.user.model.User;
import com.jeremy.service.base.BaseService;
import com.jeremy.service.exception.BusinessException;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 15:04
 * @Description:
 */
public interface UserService extends BaseService<User> {

    /**
     * 登录
     * @param username  用户名
     * @param password  密码
     * @return
     */
    User login(String username, String password) throws BusinessException;

    /**
     * 秒杀订单
     * @param productId
     */
    void skill(String productId);

    /**
     * 查询订单
     * @param productId
     * @return
     */
    String query(String productId);
}
