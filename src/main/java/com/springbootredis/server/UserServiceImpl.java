package com.springbootredis.server;

import com.springbootredis.dao.UserDao;
import com.springbootredis.model.User;
import com.springbootredis.model.enums.UserType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 15:05
 * @Description:
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User>  implements UserService{

}
