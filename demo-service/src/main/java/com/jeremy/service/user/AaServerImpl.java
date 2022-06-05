package com.jeremy.service.user;

import com.jeremy.data.user.enums.UserType;
import com.jeremy.data.user.model.User;
import com.jeremy.service.annotation.WriteTransactional;
import com.jeremy.service.base.BaseService;
import com.jeremy.service.exception.BusinessException;
import com.jeremy.service.exception.ResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: laizc
 * @date: created in 2022/1/8
 * @desc:
 **/
@Service
public class AaServerImpl implements AaServer{

    @Autowired
    private UserService userService;

    @Autowired
    private BbServer bbServer;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = BusinessException.class)
    public void add(String name) throws BusinessException {
        User user = new User();
        user.setRoleId("2");
        user.setUsername(name);
        user.setAge(28);
        user.setTop(false);
        user.setUserType(UserType.NORMAL);
        userService.save(user);
        //throw new BusinessException(ResponseCodes.TOKEN_FAILURE);
        bbServer.addOne(name);


    }
}
