package com.jeremy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.jeremy.data.**.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Docket buildDocket() {
		List<Parameter> list = Arrays.asList(
				new ParameterBuilder()
						.name("test")
						.defaultValue("test")
						.description("访问令牌")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.build()
		);
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(buildApiInfo()).globalOperationParameters(list).select()
				.apis(RequestHandlerSelectors.basePackage("com"))
				.paths(PathSelectors.any())
				.build()
				.securityContexts( Collections.singletonList(buildSecurityContexts()))
				.securitySchemes(Collections.singletonList(buildSecuritySchemes()));
	}

	private SecurityScheme buildSecuritySchemes() {
		return new ApiKey("test", "test", "header");
	}

	private SecurityContext buildSecurityContexts() {
		return SecurityContext.builder()
				.securityReferences(Collections.singletonList(new SecurityReference("Authorization", scopes())))
				.forPaths(PathSelectors.any())
				.build();
	}

	/**
	 * 允许认证的scope
	 */
	private AuthorizationScope[] scopes() {
		AuthorizationScope authorizationScope = new AuthorizationScope("all", "接口测试");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return authorizationScopes;
	}

	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder()
				.title("swagger2Properties.getTitle()")
				.description("swagger2Properties.getDescription()")
				.version("swagger2Properties.getVersion()")
				.build();
	}

}
