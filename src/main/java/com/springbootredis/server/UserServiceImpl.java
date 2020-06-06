package com.springbootredis.server;

import com.springbootredis.exception.BusinessException;
import com.springbootredis.exception.ResponseCodes;
import com.springbootredis.model.User;
import com.springbootredis.util.encrypt.Md5xEncrypter;
import org.springframework.stereotype.Service;

/**
 * @Auther: laizc
 * @Date: 2019/1/6 15:05
 * @Description:
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User>  implements UserService{

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

    public static void main(String[] args) {
        String encryPassword = md5xEncrypter.encryptByMd5Source("21232f297a57a5a743894a0e4a801fc3",4);
        System.out.println(encryPassword);
    }
}
