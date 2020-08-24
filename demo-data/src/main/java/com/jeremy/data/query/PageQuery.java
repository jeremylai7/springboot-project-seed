package com.jeremy.data.query;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: laizc
 * @Date: Created in 15:11 2019-06-25
 */
public class PageQuery {
    @ApiModelProperty(value = "每页大小",required = true)
    private Integer pageSize;

    @ApiModelProperty(value = "页码",required = true)
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
