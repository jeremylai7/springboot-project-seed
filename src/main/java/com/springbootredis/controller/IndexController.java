package com.springbootredis.controller;

import com.springbootredis.server.IndexServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RestController
public class IndexController {
	@Autowired
	private IndexServer indexServer;

	@RequestMapping("/index")
	public List<String> index(){
		return  indexServer.index();
	}

	@RequestMapping("get/index")
	public List<String> get(){
		List<String> list = new ArrayList<>();
		list.add("uu");
		list.add("yyt");
		return list;
	}

	@RequestMapping("/login")
	public List<String> login(){
		List<String> list = new ArrayList<>();
		list.add("bbvv");
		list.add("7789");
		return  list;
	}

	@RequestMapping("manage/index")
	public  List<String> mangeindex(){
		List<String> list = new ArrayList<>();
		list.add("mange");
		return list;
	}









}
