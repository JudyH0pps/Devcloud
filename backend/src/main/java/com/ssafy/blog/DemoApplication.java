package com.ssafy.blog;

import com.ssafy.blog.config.AppProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class DemoApplication {

	public static void main(String[] args) {
		System.setProperty("user.timezone", "Asia/Seoul");
		SpringApplication.run(DemoApplication.class, args);
	}

}
