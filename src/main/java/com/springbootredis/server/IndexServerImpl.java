package com.springbootredis.server;

import com.springbootredis.dao.UserDao;
import com.springbootredis.exception.BusinessException;
import com.springbootredis.exception.ResponseCodes;
import com.springbootredis.model.User;
import com.springbootredis.model.enums.UserType;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndexServerImpl implements IndexServer{
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
        List<User> list = userDao.selectByExample(example);
        if (list.size() > 0){
            throw new BusinessException(ResponseCodes.USERNAME_EXISTING);
        }
        userDao.updateByPrimaryKey(user);
    }

    @Cacheable(value = "user1",key = "#aa",condition = "#aa.length() > 2")
	public List<String> index(String aa){
		List<String> list = new ArrayList<>();
		list.add("aa");
		list.add("bb");
		System.out.println("-------执行了-------");
		System.out.println(aa);
		return list;
	}

	@CacheEvict(value = "user1",allEntries = false,key = "#aa")
	public List<String> remove(String aa){
		System.out.println(12);
		return null;
	}

	@CachePut(value = "user",key = "#aa")
	public List<String> cachePut(String aa){
		List<String> list = new ArrayList<>();
		list.add("aa");
		list.add("cc");
		return list;
	}


    public static void main(String[] args) {
        JedisShardInfo jedisShardInfo = new JedisShardInfo("47.98.20.133",6379,10000);
        jedisShardInfo.setPassword("");
        Jedis jedis = new Jedis(jedisShardInfo);
        jedis.connect();
        jedis.set("name","jeremy");
        System.out.println(jedis.get("name"));
        //jedis.connect();
        jedis.close();


    }


}
