package com.jeremy.service.redis;

/**
 * @Author: laizc
 * @Date: Created in 10:04 2018-12-18
 */
public interface RedisService {
    /**
     *
     * @param key key
     * @param doamin 对象
     * @param expire 过期时间 单位秒  -1不设置过期时间
     */
    void put(String key, Object doamin, long expire);

    /**
     *
     * @param key key
     */
    Object get(String key);
}
