package com.jeremy.service.user;

/**
 * @Author: laizc
 * @Date: Created in  2021-10-25
 * @desc: 秒杀
 */
public interface SecSkillService {

	/**
	 * 秒杀订单
	 * @param productId
	 */
	void skill(String productId);

	/**
	 * 查询订单
	 * @param productId
	 * @return
	 */
	String query(String productId);

}
