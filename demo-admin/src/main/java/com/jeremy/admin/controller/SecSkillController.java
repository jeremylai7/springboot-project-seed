package com.jeremy.admin.controller;

import com.jeremy.data.user.model.User;
import com.jeremy.service.exception.BusinessException;
import com.jeremy.service.user.SecSkillService;
import com.jeremy.service.user.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: laizc
 * @Date: Created in  2021-03-05
 * @desc: 秒杀系统设计
 */
@Api(description = "秒杀接口")
@CrossOrigin
@RestController
@RequestMapping("/skill")
public class SecSkillController {

	@Autowired
    private SecSkillService secSkillService;

	@GetMapping("/skill")
	public String skill(String productId) throws BusinessException {
		secSkillService.skill(productId);
		return secSkillService.query(productId);
	}

	@GetMapping("/query")
	public String query(String productId) {
		return secSkillService.query(productId);
	}


}
