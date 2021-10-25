package com.jeremy.service.user;

import com.jeremy.service.exception.BusinessException;
import com.jeremy.service.exception.ResponseCodes;
import com.jeremy.service.redis.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: laizc
 * @Date: Created in  2021-10-25
 * @desc:
 */
@Service
public class SecSkillServiceImpl implements SecSkillService{

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

	private static final int TIME_OUT = 10 * 1000;

	@Autowired
	private RedisLock redisLock;

	@Override
	public synchronized void skill(String productId) throws BusinessException {
		//加锁
		long time = System.currentTimeMillis() + TIME_OUT;
		if (!redisLock.lock(productId, String.valueOf(time))) {
			throw new BusinessException(ResponseCodes.FAIL);
		}
		//查询库存
		int stockNum = stock.get(productId);
		if (stockNum == 0) {
			throw new RuntimeException("活动结束");
		} else {
			//下单
			orders.put(UUID.randomUUID().toString(),productId);
			//减库存
			stockNum = stockNum -1;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			stock.put(productId,stockNum);
		}
		//解锁
		redisLock.unlock(productId,String.valueOf(time));

	}

	@Override
	public String query(String productId) {
		return "国庆活动，皮蛋粥特价，限量份"
				+ products.get(productId)
				+" 还剩：" + stock.get(productId)+" 份"
				+" 该商品成功下单用户数目："
				+  orders.size() +" 人" ;
	}

}
