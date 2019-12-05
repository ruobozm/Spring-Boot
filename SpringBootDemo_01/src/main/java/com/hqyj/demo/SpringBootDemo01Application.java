package com.hqyj.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.Banner;;

@SpringBootApplication
public class SpringBootDemo01Application {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(SpringBootDemo01Application.class);
		// 关闭banner
//		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run();
	}

}
