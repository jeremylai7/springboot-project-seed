package com.springbootredis.controller;

import com.springbootredis.model.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: laizc
 * @Date: Created in 10:41 2019-08-29
 */
@Api(value = "其他示例api",description = "其他示例api desc")
@RestController
@RequestMapping("/example")
public class ExampleController {
    @GetMapping("/index")
    public Result index(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        System.out.println(session.isNew());

        return null;
    }
}
