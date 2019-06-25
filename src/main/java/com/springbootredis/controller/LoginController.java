package com.springbootredis.controller;

import com.alibaba.fastjson.JSONObject;
import com.springbootredis.exception.BusinessException;
import com.springbootredis.exception.ResponseCodes;
import com.springbootredis.model.Result;
import com.springbootredis.model.User;
import com.springbootredis.model.enums.UserType;
import com.springbootredis.redis.RedisService;
import com.springbootredis.server.User2Service;
import com.springbootredis.server.UserService;
import com.springbootredis.util.JwtUtil;
import com.springbootredis.util.NetUtil;
import com.springbootredis.util.OutUtil;
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
import java.util.List;
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
    public Result login(@ApiIgnore HttpServletRequest request,@ApiIgnore HttpServletResponse response,String username,
                        String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        int count = userService.selectCount(user);
        if (count < 1){
            //密码错误
            throw new BusinessException(ResponseCodes.PASSWORD_ERROR);
        }
        //传入token参数
        Map<String,Object> map = new HashMap<>();
        String ip = NetUtil.getIpAddress(request);
        map.put("ip",ip);
        map.put("user", JSONObject.toJSONString(user));
        String token = JwtUtil.generateToken(map);
        response.setHeader("Authorization",token);
        redisService.put(token,user.getId(),60*60);
        JSONObject data = new JSONObject();
        data.put("records", user);
        return OutUtil.success(null);
    }
}
