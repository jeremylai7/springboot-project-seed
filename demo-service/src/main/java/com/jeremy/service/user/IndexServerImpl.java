package com.jeremy.service.user;

import com.jeremy.common.encrypt.Md5xEncrypter;
import com.jeremy.data.query.PageQuery;
import com.jeremy.data.user.dao.UserDao;
import com.jeremy.data.user.enums.UserType;
import com.jeremy.data.user.model.User;
import com.jeremy.service.base.BaseServiceImpl;
import com.jeremy.service.exception.BusinessException;
import com.jeremy.service.exception.ResponseCodes;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class IndexServerImpl extends BaseServiceImpl<User> implements IndexServer {

    @Resource
    private UserDao userDao;

    private static Md5xEncrypter md5xEncrypter = new Md5xEncrypter(8);

    @Override
    public void add(String username,String password) throws BusinessException {
        User oldUser = new User();
        oldUser.setUsername(username);
        int count = selectCount(oldUser);
        //用户名存在
        if (count > 0){
            throw new BusinessException(ResponseCodes.USERNAME_EXISTING);
        }
        User user = new User();
        user.setRoleId("2");
        user.setUsername(username);
        user.setAge(18);
        user.setTop(false);
        user.setUserType(UserType.NORMAL);
        userDao.insert(user);
        User newUser = new User();
        newUser.setPassword(md5xEncrypter.encryptByMd5Source(password,user.getId()));
        newUser.setId(user.getId());
        userDao.updateByPrimaryKeySelective(newUser);
    }

    @Override
    public void updateAll(User user) throws BusinessException {
        /*Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",user.getUsername());
        criteria.andNotEqualTo("id",user.getId());
        List<User> list = userDao.selectByExample(example);
        if (list.size() > 0){
            throw new BusinessException(ResponseCodes.USERNAME_EXISTING);
        }
        userDao.updateByPrimaryKey(user);*/
    }

    @Override
    public void update(User user) throws BusinessException {
        /*Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",user.getUsername());
        criteria.andNotEqualTo("id",user.getId());
        List<User> list = userDao.selectByExample(example);
        if (list.size() > 0){
            throw new BusinessException(ResponseCodes.USERNAME_EXISTING);
        }
        userDao.updateByPrimaryKeySelective(user);*/
    }

    @Override
    //@Cacheable(value = "indexUser")//每次都会生成的key都是不同的，所以每次都会生成新的缓存
    //@Cacheable(value = "indexUser",key = "'user'")//每次key都是一样，除了第一次都不会生成新的缓存
    //@Cacheable(value = "indexUser",key = "#query.pageSize+'-'+#query.pageSize")
    //@Cacheable(value="indexUser", key="#p0.pageSize+''")
    //@Cacheable(value = "indexUser",key = "#p0.pageSize+''",condition = "#p0.pageSize > 2")
    @CachePut(value = "indexUser",key = "'user'",condition = "#p0.pageSize > 2")
    public List<User> findRedis(PageQuery query) {
        /*System.out.println("--------------执行了--------------");
        int count = userDao.selectCount(null);
        if (count > 0){
            PageHelper.startPage(query.getPageNo(),query.getPageSize());
            return userDao.selectAll();
        }*/
        return null;

    }

    @Override
    //@CacheEvict(value = "indexUser",key = "'user'")
    @CacheEvict(value = "indexUser",allEntries = true)//清除所有的元素
    public void ClearnCache() {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transTest(String aa) throws Exception {
        User user = findById(55);
        user.setPassword(aa);
        int count = updateByPrimaryKey(user);
        System.out.println(count);
        User newUser = findById(55);
        System.out.println(newUser.toString());
        if (aa.equals("yy")){
            throw new Exception();
        }

    }

    public static void main(String[] args) {
        List<String> lis1 = new ArrayList<>();
        List<Integer> list2 = new LinkedList<>();
        System.out.println(11);

        /*JedisShardInfo jedisShardInfo = new JedisShardInfo("47.98.202.133",6379,10000);
        jedisShardInfo.setPassword("1234561");
        Jedis jedis = new Jedis(jedisShardInfo);
        jedis.connect();
        jedis.set("name","jeremy");
        System.out.println(jedis.get("name"));
        //jedis.connect();
        jedis.close();*/


    }


}
