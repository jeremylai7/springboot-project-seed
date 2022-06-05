package com.jeremy.service.user;

import com.jeremy.service.exception.BusinessException;

/**
 * @author: laizc
 * @date: created in 2022/1/8
 * @desc:
 **/
public interface BbServer {
    void addOne(String name) throws BusinessException;
}
