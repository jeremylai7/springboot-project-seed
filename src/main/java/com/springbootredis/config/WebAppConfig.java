package com.springbootredis.config;

import com.springbootredis.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 注册拦截器
 */
@SpringBootConfiguration
public class WebAppConfig extends WebMvcConfigurerAdapter{
	@Autowired
	private MyInterceptor myInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//注册自定义拦截器
		registry.addInterceptor(myInterceptor).addPathPatterns("/**")
				.excludePathPatterns("/user/login");

	}


}
