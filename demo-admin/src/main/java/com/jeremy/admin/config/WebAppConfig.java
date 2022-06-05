package com.jeremy.admin.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 注册拦截器
 */
@SpringBootConfiguration
public class WebAppConfig extends WebMvcConfigurationSupport {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//注册自定义拦截器
		//registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**")
			//	.excludePathPatterns("/user/login");
	}

	/**
	 * 配置访问静态资源
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 解决controller返回字符串中文乱码问题
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof StringHttpMessageConverter) {
				((StringHttpMessageConverter)converter).setDefaultCharset(StandardCharsets.UTF_8);
			}
		}
	}

}
