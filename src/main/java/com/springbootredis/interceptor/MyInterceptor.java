package com.springbootredis.interceptor;


import com.springbootredis.annotation.Logined;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class MyInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
		System.out.println(o);
		HandlerMethod handlerMethod = (HandlerMethod) o;
		if (!(handlerMethod instanceof HandlerMethod)) {
			return true;
		}
		Method method = handlerMethod.getMethod();
		System.out.println(method);
		boolean isLogin = this.isLogin(method);
		if (!isLogin){
			return true;
		}
		if (httpServletRequest.getServletPath().startsWith("/manage")){
			httpServletResponse.sendRedirect("/login");
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
