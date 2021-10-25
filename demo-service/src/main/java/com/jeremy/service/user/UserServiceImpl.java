package com.jeremy.service.user;

import com.jeremy.common.encrypt.Md5xEncrypter;
import com.jeremy.data.user.enums.UserType;
import com.jeremy.data.user.model.User;
import com.jeremy.service.annotation.WriteTransactional;
import com.jeremy.service.base.BaseServiceImpl;
import com.jeremy.service.exception.BusinessException;
import com.jeremy.service.exception.ResponseCodes;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 15:05
 * @Description:
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{

    private static Md5xEncrypter md5xEncrypter = new Md5xEncrypter(8);

    @Override
    public User login(String username, String password) throws BusinessException {
        User oldUser =  new User();
        oldUser.setUsername(username);
        User user = selectOne(oldUser);
        if (user == null){
            throw new BusinessException(ResponseCodes.PASSWORD_ERROR);
        }
        String encryPassword = md5xEncrypter.encryptByMd5Source(password,user.getId());
        if (!encryPassword.equals(user.getPassword())){
            throw new BusinessException(ResponseCodes.PASSWORD_ERROR);
        }
        return user;
    }


    @Override
    @WriteTransactional
    public void add(String username, String password) {
        User user = new User();
        user.setRoleId("2");
        user.setUsername(username);
        user.setAge(18);
        user.setTop(false);
        user.setUserType(UserType.NORMAL);
        save(user);
        add();
    }

    private void add() {
        int a = 1/0;
    }


    public static void main(String[] args) {
        String encryPassword = md5xEncrypter.encryptByMd5Source("21232f297a57a5a743894a0e4a801fc3",4);
        System.out.println(encryPassword);
    }
}
