package com.jeremy.data.query;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: laizc
 * @Date: Created in 15:11 2019-06-25
 */
public class PageQuery {
    //@ApiModelProperty(value = "每页大小")
    private Integer pageSize = 10;

    //@ApiModelProperty(value = "页码")
    private Integer pageNo = 1;

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
