package com.springbootredis.server;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexServer {
	@Cacheable(value = "user")
	public List<String> index(){
		List<String> list = new ArrayList<>();
		list.add("aa");
		list.add("bb");
		System.out.println("-------执行了-------");
		return list;
	}

	@CacheEvict(value = "user")
	public List<String> remove(){
		System.out.println(12);
		return null;
	}

}
