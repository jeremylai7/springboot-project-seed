package com.springbootredis.controller;

import com.springbootredis.annotation.Logined;
import com.springbootredis.model.Result;
import com.springbootredis.model.User;
import com.springbootredis.redis.RedisService;
import com.springbootredis.server.IndexServer;
import com.springbootredis.server.UserService;
import com.springbootredis.util.OutUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Logined
@Api("首页api")
@RestController
public class IndexController {
	@Autowired
	private IndexServer indexServer;

	@Autowired
	private UserService userService;

	@Autowired
	private RedisService redisService;

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
