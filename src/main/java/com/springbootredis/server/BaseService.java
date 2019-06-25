package com.springbootredis.server;

import java.util.List;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 14:58
 * @Description:
 */
public interface BaseService<T> {
    /**
     * 查询所有
     *
     * @return 返回所有数据
     */
    List<T> findAll();

    /**
     * 添加
     *
     * @param t   实体
     *
     * @return
     */
    int save(T t);

    /**
     * 修改
     *
     * @param t
     *            实体
     * @return
     */
    int updateByPrimaryKey(T t);

    /**
     * 根据主键删除
     *
     * @param t   主键
     *
     * @return
     */
    int deleteByPrimaryKey(int t);

    /**
     * 查询数量
     * @param t
     * @return
     */
    int selectCount(T t);
}
