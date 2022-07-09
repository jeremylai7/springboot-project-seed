package com.jeremy.service.user;

import com.jeremy.data.user.model.User;
import com.jeremy.data.user.dao.UserMapper;
import com.jeremy.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @Auther: Jeremy
 * @Date: 2022-07-09
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

       @Resource
       private UserMapper userMapper;

}
