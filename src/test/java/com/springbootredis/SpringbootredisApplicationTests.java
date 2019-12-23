package com.springbootredis;

import com.springbootredis.exception.BusinessException;
import com.springbootredis.model.User;
import com.springbootredis.redis.RedisService;
import com.springbootredis.server.IndexServer;
import com.springbootredis.util.OutUtil;
import com.springbootredis.util.encrypt.Md5Encrypter;
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

	@Autowired
	private IndexServer indexServer;

	@Test
	public void contextLoads() throws BusinessException {
//		User user = new User();
//		user.setUsername("sfsfs");
//		user.setPassword("fefe");
//		user.setAge(17);
//
		redisService.put("jeremy","jss",20);
		int len = 100000;
		for (int i = 0; i <len ; i++) {
			String password = "ervbd"+i;
			indexServer.add("apple"+i, Md5Encrypter.encrypt32(password));
		}
	}

}
