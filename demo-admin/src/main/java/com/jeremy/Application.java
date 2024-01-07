package com.jeremy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.jeremy.data.**.dao")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
