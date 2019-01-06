package com.springbootredis.controller;

import com.springbootredis.model.User;
import com.springbootredis.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 14:48
 * @Description: 登陆
 */
@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public String login(String username,String password){
        List<User> list = userService.findAll();
        System.out.println(list);
        return null;
    }

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/add")
    public String add(String username,String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.save(user);
        return null;
    }
}
