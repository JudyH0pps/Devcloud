package com.ssafy.blog;

import com.ssafy.blog.config.AppProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class DemoApplication {

	public static void main(String[] args) {
		// System.setProperty("user.timezone", "Asia/Seoul");
		// SpringApplication.run(DemoApplication.class, args);
		SpringApplication app = new SpringApplication(DemoApplication.class);
		// pid 파일을 생성하는 writer 등록
		app.addListeners(new ApplicationPidFileWriter());
		app.run(args);
	}

}
