package com.springbootredis.server;

import com.springbootredis.dao.UserDao;
import com.springbootredis.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 15:05
 * @Description:
 */
@Service
public class UserServiceImpl  implements UserService {
    @Resource
    private UserDao userDao;


    @Override
    public List<User> find() {
        return userDao.selectAll();
    }

    @Override
    public User findById(User user) {
        return userDao.selectOne(user);
    }

    @Override
    public int add(User user) {
        return userDao.insert(user);
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }
}
