package com.springbootredis.util;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 14:54
 * @Description:
 */
@Repository
public interface MyMapper<T> extends Mapper<T> {
}
