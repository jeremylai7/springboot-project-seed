package com.jeremy.service.user;

import com.jeremy.service.exception.BusinessException;
import com.jeremy.service.exception.ResponseCodes;
import org.springframework.stereotype.Service;

/**
 * @author: laizc
 * @date: created in 2022/1/8
 * @desc:
 **/
@Service
public class BbServerImpl implements BbServer{

    @Override
    public void addOne(String name) throws BusinessException {
        if (name.startsWith("b")) {
            throw new BusinessException(ResponseCodes.PASSWORD_ERROR);
        }


    }
}
