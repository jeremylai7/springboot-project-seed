package com.jeremy.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author: laizc
 * @date: created in 2022/5/16
 * @desc:
 **/
@RestController
@RequestMapping("/example")
public class ExampleController {

    @GetMapping("/index")
    public Object index(HttpServletRequest request){
        Demo demo = new Demo();
        demo.setCreateTime(new Date());
        demo.setAa("3535ddd");
        return demo;
    }
}
