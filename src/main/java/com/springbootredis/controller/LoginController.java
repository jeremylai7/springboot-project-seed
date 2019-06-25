package com.springbootredis.controller;

import com.springbootredis.exception.BusinessException;
import com.springbootredis.model.Result;
import com.springbootredis.model.User;
import com.springbootredis.model.enums.UserType;
import com.springbootredis.redis.RedisService;
import com.springbootredis.server.User2Service;
import com.springbootredis.server.UserService;
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
import java.util.List;

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

    @Autowired
    private User2Service user2Service;

    @ApiOperation(value = "测试")
    @GetMapping("/test")
    public Result test(){
        System.out.println("test");
        return OutUtil.success("");
    }

    @ApiOperation(value = "登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名",name = "username",required = true,paramType = "form"),
            @ApiImplicitParam(value = "密码",name ="password",required = true,paramType = "form")
    })
    @PostMapping("/login")
    public Result login(@ApiIgnore HttpServletRequest request,@ApiIgnore HttpServletResponse response,String username,
                        String password) throws Exception {
        /*User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        User list = userService.findById(user);
        if (list == null){
            throw new BusinessException(ResponseCodes.PASSWORD_ERROR);//密码错误
        }
        //传入token参数
        Map<String,Object> map = new HashMap<>();
        String ip = NetUtil.getIpAddress(request);
        map.put("ip",ip);
        map.put("user", JSONObject.toJSONString(list));
        String token = JwtUtil.generateToken(map);
        response.setHeader("Authorization",token);
        redisService.put(token,list.getId(),60*60);
        JSONObject data = new JSONObject();
        data.put("records", user);*/
        return OutUtil.success(null);
    }

    @ApiOperation(value = "添加用户",response = User.class)
    @PostMapping("/add")
    public Result add(@ApiIgnore HttpServletRequest request,User user) throws BusinessException {
        /*User user1 = userService.findById(user);
        if (user1 != null){
            throw new BusinessException(ResponseCodes.USERNAME_EXISTING);//用户名不能重复
        }
        int len = 100000000;
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i <len ; i++) {
            User user2 = new User();
            user2.setUsername("小明"+i);
            user2.setPassword("142424");
            user.setAge(23);
            user2Service.add(user);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("时间差"+(endTime - beginTime));*/

        return OutUtil.success("添加成功");
    }

    /**
     * 查询所有信息
     * @return
     */
    @ApiOperation(value = "获取所有用户",response = User.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名",name = "username",paramType = "query")
    })
    @GetMapping("/list")
    public Result list(String username, @ApiIgnore HttpServletRequest request){
      /* long beginTime = System.currentTimeMillis();
       List<User> list = userService.find();
       long endTime = System.currentTimeMillis();
       System.out.println("========================>查询时间"+(endTime - beginTime));
       return OutUtil.success(list);*/
      return OutUtil.success(null);
    }

    @ApiOperation(value = "查询不同状态用户",response = User.class)
    @GetMapping("/findByState")
    public Result findByState(UserType userType){
        List<User> list = user2Service.getUser(userType);
        //List<User> list = userService.findAll(type);
        return OutUtil.success(list);
    }

}
