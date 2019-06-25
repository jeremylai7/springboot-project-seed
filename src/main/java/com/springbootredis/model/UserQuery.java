package com.springbootredis.model;

/**
 * @Author: laizc
 * @Date: Created in 15:11 2019-06-25
 */
public class UserQuery {
    private Integer pageSize;

    private Integer pageNo;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
