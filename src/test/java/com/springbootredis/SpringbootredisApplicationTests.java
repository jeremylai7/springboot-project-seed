package com.springbootredis;

import com.springbootredis.model.User;
import com.springbootredis.redis.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootredisApplicationTests {
	@Autowired
	private RedisService redisService;

	@Test
	public void contextLoads() {
//		User user = new User();
//		user.setUsername("sfsfs");
//		user.setPassword("fefe");
//		user.setAge(17);
//
		redisService.put("jeremy","jss",20);


	}

}
