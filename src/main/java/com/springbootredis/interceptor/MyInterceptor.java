package com.springbootredis.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.springbootredis.annotation.Logined;
import com.springbootredis.exception.BusinessException;
import com.springbootredis.exception.ResponseCodes;
import com.springbootredis.model.User;
import com.springbootredis.redis.RedisService;
import com.springbootredis.util.JwtUtil;
import com.springbootredis.util.NetUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

@Component
public class MyInterceptor implements HandlerInterceptor{
	@Autowired
	private RedisService redisService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) o;
		if (!(handlerMethod instanceof HandlerMethod)) {
			return true;
		}
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
		Integer userId = Integer.valueOf(redisService.get(authorization).toString());
		if (redisService.get(authorization) == null){
			throw new BusinessException(ResponseCodes.TOKENOVERDUE);
		}
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

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
		System.out.println("post");
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
		System.out.println("after");
	}
}
