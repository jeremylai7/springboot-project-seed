package com.springbootredis.controller;

import com.springbootredis.annotation.Logined;
import com.springbootredis.exception.BusinessException;
import com.springbootredis.model.Result;
import com.springbootredis.model.User;
import com.springbootredis.redis.RedisService;
import com.springbootredis.server.IndexServerImpl;
import com.springbootredis.server.UserService;
import com.springbootredis.util.OutUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Logined
@Api("首页api")
@RestController
@RequestMapping("/user")
public class IndexController {
	@Autowired
	private IndexServerImpl indexServer;

	@Autowired
	private UserService userService;

	@Autowired
	private RedisService redisService;

	@ApiOperation(value = "添加用户")
    @PostMapping("/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",paramType = "query",required = true),
            @ApiImplicitParam(name = "password",value = "密码",paramType = "query",required = true)
    })
    public Result add(String username,String password) throws BusinessException {
	    indexServer.add(username,password);
	    return OutUtil.success(null);
	}

	@ApiOperation(value = "全修改用户信息")
    @PostMapping("/update-all")
    public Result updateAll(User user) throws BusinessException {
        indexServer.updateAll(user);
	    return OutUtil.success(null);
    }


	@ApiOperation(value = "添加",response = User.class)
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public List<String> index(String aa){
		return  indexServer.index(aa);
	}

	@RequestMapping(value = "get/index",method = RequestMethod.GET)
	public List<String> get(){
		List<String> list = new ArrayList<>();
		list.add("uu");
		list.add("yyt");
		return list;
	}

	@ApiOperation(value = "获取所有用户",response = User.class)
	@GetMapping("/getList")
	public Result getList(){
		List<User> list = userService.find();
		return OutUtil.success(list);
	}

	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public List<String> login(){
		List<String> list = new ArrayList<>();
		list.add("bbvv");
		list.add("7789");
		return  list;
	}

	@RequestMapping(value = "/remove",method = RequestMethod.GET)
	public void remove(String aa){
		indexServer.remove(aa);
	}

	@GetMapping("/put")
	public void put(String aa){
		indexServer.cachePut(aa);
	}









}
