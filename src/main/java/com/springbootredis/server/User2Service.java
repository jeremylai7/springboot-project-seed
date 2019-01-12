package com.springbootredis.server;

import com.springbootredis.dao.User2Dao;
import com.springbootredis.dao.UserDao;
import com.springbootredis.model.User;
import com.springbootredis.model.enums.UserType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: laizc
 * @Date: 2019/1/12 20:19
 * @Description:
 */
@Service
public class User2Service {
    @Resource
    private User2Dao user2Dao;

    public List<User> getUser(UserType state){
        System.out.println(state);
        return user2Dao.findByType(state);
    }

    public void add(User user){
        user.setId(null);
        user.setTop(false);
        user2Dao.add(user);
    }



}
