package com.springbootredis.util;

import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 14:54
 * @Description:
 */
@Mapper
public interface MyMapper<T> extends
        tk.mybatis.mapper.common.Mapper{
}
