package com.springbootredis.server;

import com.github.pagehelper.PageHelper;
import com.springbootredis.dao.UserDao;
import com.springbootredis.exception.BusinessException;
import com.springbootredis.exception.ResponseCodes;
import com.springbootredis.model.User;
import com.springbootredis.model.UserQuery;
import com.springbootredis.model.enums.UserType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexServerImpl extends BaseServiceImpl<User> implements IndexServer {
    @Resource
    private UserDao userDao;

    @Override
    public void add(String username,String password) throws BusinessException {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        List<User> list = userDao.selectByExample(example);
        if (list.size() > 0){
            //用户名存在
            throw new BusinessException(ResponseCodes.USERNAME_EXISTING);
        }
        User user = new User();
        user.setRoleId("2");
        user.setUsername(username);
        user.setPassword(password);
        user.setAge(18);
        user.setTop(false);
        user.setUserType(UserType.NORMAL);
        userDao.insert(user);
	}

    @Override
    public void updateAll(User user) throws BusinessException {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",user.getUsername());
        criteria.andNotEqualTo("id",user.getId());
        List<User> list = userDao.selectByExample(example);
        if (list.size() > 0){
            throw new BusinessException(ResponseCodes.USERNAME_EXISTING);
        }
        userDao.updateByPrimaryKey(user);
    }

    @Override
    public void update(User user) throws BusinessException {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",user.getUsername());
        criteria.andNotEqualTo("id",user.getId());
        List<User> list = userDao.selectByExample(example);
        if (list.size() > 0){
            throw new BusinessException(ResponseCodes.USERNAME_EXISTING);
        }
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> find(UserQuery query) {
        int count = userDao.selectCount(null);
        if (count > 0){
            PageHelper.startPage(query.getPageNo(),query.getPageSize());
            return userDao.selectAll();
        }
        return null;
    }

    @Override
    @Cacheable(value = "indexUser",key = "'user'")
    public List<User> findRedis(UserQuery query) {
        System.out.println("--------------执行了--------------");
        int count = userDao.selectCount(null);
        if (count > 0){
            PageHelper.startPage(query.getPageNo(),query.getPageSize());
            return userDao.selectAll();
        }
        return null;

    }
    public static void main(String[] args) {
        JedisShardInfo jedisShardInfo = new JedisShardInfo("47.98.202.133",6379,10000);
        jedisShardInfo.setPassword("123451");
        Jedis jedis = new Jedis(jedisShardInfo);
        jedis.connect();
        jedis.set("name","jeremy");
        System.out.println(jedis.get("name"));
        //jedis.connect();
        jedis.close();


    }


}
