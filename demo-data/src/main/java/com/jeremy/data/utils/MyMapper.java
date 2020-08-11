package com.jeremy.data.utils;


import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 14:54
 * @Description:
 */
public interface MyMapper<T> extends
		BaseMapper<T>,
		ConditionMapper<T>,
		IdsMapper<T>,
		InsertListMapper<T> {
}
