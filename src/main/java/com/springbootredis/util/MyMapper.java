package com.springbootredis.util;

import com.springbootredis.model.User;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 14:54
 * @Description:
 */
public interface MyMapper<T> extends Mapper<T> {
}
