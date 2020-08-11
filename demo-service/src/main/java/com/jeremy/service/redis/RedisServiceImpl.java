package com.jeremy.service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: laizc
 * @Date: Created in 10:05 2018-12-18
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void put(String key,Object doamin,long expire) {
        //设置编码
        RedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        ValueOperations<String,Object> ov = redisTemplate.opsForValue();
        if (expire == -1){
            ov.set(key,doamin);
        }else {
            ov.set(key,doamin,expire,TimeUnit.SECONDS);
        }
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
