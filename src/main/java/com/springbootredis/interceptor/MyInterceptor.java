package com.springbootredis.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.springbootredis.annotation.Logined;
import com.springbootredis.exception.BusinessException;
import com.springbootredis.exception.ResponseCodes;
import com.springbootredis.model.User;
import com.springbootredis.redis.RedisService;
import com.springbootredis.util.JwtUtil;
import com.springbootredis.util.NetUtil;
import com.springbootredis.util.ProjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;


public class MyInterceptor extends HandlerInterceptorAdapter{

	private RedisService redisService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("==================================>" + request.getServletPath() + "+++method:" + request.getMethod());
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
		if (redisService == null){
			redisService = (RedisService) factory.getBean("redisServiceImpl");
		}
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
