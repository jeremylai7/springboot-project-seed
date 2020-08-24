package com.jeremy.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.jeremy.data.user.model.User;
import com.jeremy.demo.web.core.utils.NetUtil;
import com.jeremy.demo.web.core.utils.OutUtil;
import com.jeremy.demo.web.core.view.Result;
import com.jeremy.service.redis.RedisService;
import com.jeremy.service.user.UserService;
import com.jeremy.service.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 14:48
 * @Description:
 */
@Api(value = "用户api",description = "用户api desc")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名",name = "username",required = true,paramType = "form"),
            @ApiImplicitParam(value = "密码",name ="password",required = true,paramType = "form")
    })
    @PostMapping("/login")
    public Result login(@ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response, String username,
                        String password) throws Exception {
        User user = userService.login(username,password);
        //传入token参数
        Map<String,Object> map = new HashMap<>();
        String ip = NetUtil.getIpAddress(request);
        map.put("ip",ip);
        map.put("user", JSONObject.toJSONString(user));
        map.put("loginTime", System.currentTimeMillis());
        String token = JwtUtil.generateToken(map);
        response.setHeader("Authorization",token);
        redisService.put(token,user.getId(),60*60);
        return OutUtil.success(null);
    }

    @ApiOperation(value = "test")
    @GetMapping("/test")
    public Result test(@RequestParam(value = "aaa") String username){
        System.out.println(username);
        System.out.println(1111111);
        return OutUtil.success(null);
    }
}
