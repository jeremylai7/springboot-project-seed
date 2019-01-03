package com.springbootredis.server;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import sun.util.locale.provider.JRELocaleConstants;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexServer {
	@Cacheable(value = "user1",key = "#aa",condition = "#aa.length() > 2")
	public List<String> index(String aa){
		List<String> list = new ArrayList<>();
		list.add("aa");
		list.add("bb");
		System.out.println("-------执行了-------");
		System.out.println(aa);
		return list;
	}

	@CacheEvict(value = "user1",allEntries = false,key = "#aa")
	public List<String> remove(String aa){
		System.out.println(12);
		return null;
	}

	@CachePut(value = "user",key = "#aa")
	public List<String> cachePut(String aa){
		List<String> list = new ArrayList<>();
		list.add("aa");
		list.add("cc");
		return list;
	}


    public static void main(String[] args) {
        JedisShardInfo jedisShardInfo = new JedisShardInfo("47.98.20.133",6379,10000);
        jedisShardInfo.setPassword("");
        Jedis jedis = new Jedis(jedisShardInfo);
        jedis.connect();
        jedis.set("name","jeremy");
        System.out.println(jedis.get("name"));
        //jedis.connect();
        jedis.close();


    }

}
