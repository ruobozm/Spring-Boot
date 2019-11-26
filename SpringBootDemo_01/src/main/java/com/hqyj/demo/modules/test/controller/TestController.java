package com.hqyj.demo.modules.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqyj.demo.modules.test.vo.ApplicationConfigTestBean;

@Controller
public class TestController {
	
	//通过resources根目录的Application.properties文件，直接使用@Value获得属性值
	@Value("${server.port}")
	private  int port;
	@Value("${com.hqyj.name}")
	private String name;
	@Value("${com.hqyj.age}")
	private int age;
	@Value("${com.hqyj.description}")
	private String description;
	@Value("${com.hqyj.random}")
	private String random;
	
	// 针对其他配置文件，注入其对应的配置类
	@Autowired
	private ApplicationConfigTestBean configTestBean;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

	
	
	/**
	 * <p>
	 * 获取配置文件
	 * </p>
	 * @author zm
	 * @Date 2019年11月26日
	 * @return
	 */
	@RequestMapping("/test/getConfig")
	@ResponseBody
	public String getConfig() {
		StringBuffer sb = new StringBuffer();
		sb.append(port)
		.append("----").append(name).append("<br>")
		.append("----").append(age).append("<br>")
		.append("----").append(description).append("<br>")
		.append("----").append(random).append("<br>");
		
		sb.append(configTestBean.getName())
		.append("‐‐‐‐").append(configTestBean.getAge()).append("<br>")
		.append("‐‐‐‐").append("<br>")
		.append(configTestBean.getDescription()).append("<br>")
		.append("‐‐‐‐").append("<br>")
		.append(configTestBean.getRandom()).append("</br>");

		return sb.toString();
	}
	
	@RequestMapping("/test/testInfo")
	@ResponseBody
	public String TestInfo() {
		return "This is TestInfo";
	}
	
	
	/**
	 * <p>
	 * 日志文件logback
	 * </p>
	 * @author zm
	 * @Date 2019年11月26日
	 * @return
	 */
	@RequestMapping("/test/log")
	@ResponseBody
	public String loggerTest() {
		LOGGER.trace("33333This is trace log");
		LOGGER.debug("This is debug log");
		LOGGER.info("This is info log");
		LOGGER.warn("This is warn log");
		LOGGER.error("This is error log");
		
		return "This is logger test.";
	}
	

}
