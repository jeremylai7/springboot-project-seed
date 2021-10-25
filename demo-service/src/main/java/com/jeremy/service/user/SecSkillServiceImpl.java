package com.jeremy.service.user;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

	private Lock lock = new ReentrantLock();

	@Override
	public synchronized void skill(String productId) {
		//lock.lock();
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
			//lock.unlock();
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

}
