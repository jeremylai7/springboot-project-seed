package com.springbootredis.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: laizc
 * @Date: Created in 10:05 2018-12-18
 */
@Service
public class RedisServiceImpl implements RedisService {


    @Override
    public void put(String key,Object doamin,long expire) {

    }
}
