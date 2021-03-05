package com.jeremy.service.user;

import com.jeremy.common.encrypt.Md5xEncrypter;
import com.jeremy.data.user.model.User;
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

    private static Map<String,Integer> products;

    private static Map<String,Integer> stock;

    private static Map<String,String> orders;

    static {
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", 100000);
        stock.put("123456", 100000);
    }

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

    private Lock lock = new ReentrantLock();

    @Override
    public void skill(String productId) {
        lock.lock();
        try {
            //查询库存
            int stockNum = stock.get(productId);
            if (stockNum == 0) {
                throw new RuntimeException("活动结束");
            } else {
                //下单
                orders.put(UUID.randomUUID().toString(),productId);
                //减库存
                stockNum = stockNum -1;
                stock.put(productId,stockNum);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public String query(String productId) {
        return "国庆活动，皮蛋粥特价，限量份"
                + products.get(productId)
                +" 还剩：" + stock.get(productId)+" 份"
                +" 该商品成功下单用户数目："
                +  orders.size() +" 人" ;
    }

    public static void main(String[] args) {
        String encryPassword = md5xEncrypter.encryptByMd5Source("21232f297a57a5a743894a0e4a801fc3",4);
        System.out.println(encryPassword);
    }
}
