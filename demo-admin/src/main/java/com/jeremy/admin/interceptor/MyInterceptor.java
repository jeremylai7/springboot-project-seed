package com.jeremy.admin.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.jeremy.admin.utils.ProjectUtil;
import com.jeremy.data.user.model.User;
import com.jeremy.demo.web.core.authorize.annotation.Logined;
import com.jeremy.demo.web.core.utils.NetUtil;
import com.jeremy.service.exception.BusinessException;
import com.jeremy.service.exception.ResponseCodes;
import com.jeremy.service.redis.RedisService;
import com.jeremy.service.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public class MyInterceptor extends HandlerInterceptorAdapter{

	private RedisService redisService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("==================================>{},+++method:{}",request.getServletPath(),request.getMethod());
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		boolean isLogin = this.isLogin(method);
		if (!isLogin){
			return true;
		}
		String authorization =  request.getHeader("Authorization");
		String ip = NetUtil.getIpAddress(request);
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.setHeader("Authorization", authorization);
		Map<String,Object> params = JwtUtil.validate(authorization,ip);
		User user = JSONObject.parseObject(params.get("user").toString(),User.class);
		ProjectUtil.setUser(request,user);
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		//获取bean第一种方法
		if (redisService == null){
			redisService = (RedisService) factory.getBean("redisServiceImpl");
		}
		//获取bean第二种方法
		/*if (redisService == null){
			redisService = (RedisService) SpringContextUtil.getBean("redisServiceImpl");
		}*/
		if (redisService.get(authorization) == null){
			throw new BusinessException(ResponseCodes.TOKENOVERDUE);
		}
		Integer userId = Integer.valueOf(redisService.get(authorization).toString());
		if (!userId.equals(user.getId())){
			throw new BusinessException(ResponseCodes.TOKENOVERDUE);
		}
		redisService.put(authorization,userId,60*60);

		if (request.getServletPath().startsWith("/manage")){
			response.sendRedirect("/login");
			return false;
		}
		return true;
	}

	private boolean isLogin(Method method) {
		//获取方法头部值
		Logined classLogined = method.getDeclaringClass().getAnnotation(Logined.class);
		Logined methodLogined = method.getAnnotation(Logined.class);
		if (classLogined != null && methodLogined == null) {
			System.out.println(classLogined.isLogined());
			return classLogined.isLogined();
		}
		if ((classLogined != null && methodLogined != null) || (classLogined == null && methodLogined != null)) {
			return methodLogined.isLogined();
		}
		return false;
	}
}
