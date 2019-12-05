package com.hqyj.demo.modules.test.controller;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.pagehelper.PageInfo;
import com.hqyj.demo.modules.test.entity.City;
import com.hqyj.demo.modules.test.entity.Country;
import com.hqyj.demo.modules.test.service.TestService;
import com.hqyj.demo.modules.test.vo.ApplicationConfigTestBean;

@Controller
@RequestMapping("/test")
public class TestController {

	private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

	// 通过resources根目录的Application.properties文件，直接使用@Value获得属性值
	@Value("${server.port}")
	private int port;
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
	@Autowired
	private TestService testService;
	
	/**
	 * 下载文件
	 * 响应头信息
	 * 'Content-Type': 'application/octet-stream',
	 * 'Content-Disposition': 'attachment;filename=req_get_download.js'
	 * @return ResponseEntity ---- spring专门包装响应信息的类
	 */
	@RequestMapping("/download")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) {
		try {
			// 使用resource来包装下载文件
			Resource resource = new UrlResource(
					Paths.get("D:/upload" + File.separator + fileName).toUri());
			if (resource.exists() && resource.isReadable()) {
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + 
								resource.getFilename() + "\"")
						.body(resource);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 上传多个文件
	 */
	@RequestMapping(value="/uploadBatchFile", method=RequestMethod.POST, consumes="multipart/form-data")
	public String uploadBatchFile(@RequestParam MultipartFile[] files, 
			RedirectAttributes redirectAttributes) {
		boolean isEmpty = true;
		try {
			for (MultipartFile file : files) {
				if (file.isEmpty()) {
//					break;
					continue;
				}
				
				String fileName = file.getOriginalFilename();
				String destFileName = "D:/upload" + File.separator + fileName;
				
				File destFile = new File(destFileName);
				file.transferTo(destFile);
				
				isEmpty = false;
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "upload file fail.");
			return "redirect:/test/index";
		}
		
		if (isEmpty) {
			redirectAttributes.addFlashAttribute("message", "Please select file.");
		} else {
			redirectAttributes.addFlashAttribute("message", "upload file success.");
		}
		
		return "redirect:/test/index";
	}
	
	/**
	 * 上传单个文件，虽然是form表单，但file是以参数的形式传递的，采用requestParam注解接收MultipartFile
	 */
	@RequestMapping(value="/upload", method=RequestMethod.POST, consumes="multipart/form-data")
	public String uploadFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
		
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select file.");
			return "redirect:/test/index";
		}
		
		try {
			String fileName = file.getOriginalFilename();
			String destFileName = "D:/upload" + File.separator + fileName;
			
			File destFile = new File(destFileName);
			file.transferTo(destFile);
			
			// 使用工具类Files来上传文件
//			byte[] bytes = file.getBytes();
//			Path path = Paths.get(destFileName);
//			Files.write(path, bytes);
			
			redirectAttributes.addFlashAttribute("message", "upload file success.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "upload file fail.");
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
		}
		
		return "redirect:/test/index";
	}
	
	/**
	 * 返回test/index页面
	 * return index ---- return classpath:/templates/index.html
	 * template:test/index ---- classpath:/templates/test/index.html
	 */
	@RequestMapping("/index")
	public String testPage(ModelMap modelMap) {
		int countryId = 522;
		List<City> cities = testService.getCitiesByCountryId(countryId);
		cities = cities.stream().limit(10).collect(Collectors.toList());
		
		modelMap.addAttribute("thymeleafTitle", "This is thymeleaf test page.");
		modelMap.addAttribute("changeType", "checkbox");
		modelMap.addAttribute("checked", true);
		modelMap.addAttribute("currentNumber", 88);
		modelMap.addAttribute("baiduUrl", "/test/config");
		modelMap.addAttribute("shopLogo", "http://cdn.duitang.com/uploads/item/201308/13/20130813115619_EJCWm.thumb.700_0.jpeg");
		modelMap.addAttribute("country", testService.getcountryByCountryId(countryId));
		modelMap.addAttribute("city", cities.get(0));
		modelMap.addAttribute("cities", cities);
		modelMap.addAttribute("updateCityUri", "/test/city");
//		modelMap.addAttribute("template", "test/index");
		return "index";
	}

	/**
	 * 根据country name 查询国家信息
	 */
	@RequestMapping(value="/country",method=RequestMethod.POST)
	@ResponseBody
	public Country getCountryByCountryName(@ModelAttribute String countryName) {
		System.out.println(countryName);
		return testService.getCountryByCountryName(countryName);
	}

	/**
	 * 根据国家id查询国家信息
	 */
	@RequestMapping("/country/{countryId}")
	@ResponseBody
	public Country getcountryByCountryId(@PathVariable int countryId) {
		return testService.getcountryByCountryId(countryId);
	}

	/**
	 * 根据国家id查询所有城市
	 * 
	 * @PathVariable --- 获取url路径上的参数
	 */
	@RequestMapping("/cities/{countryId}")
	@ResponseBody
	public List<City> getCitiesByCountryId(@PathVariable int countryId) {
		return testService.getCitiesByCountryId(countryId);
	}
	
	/**
	 * 分页查询城市信息
	 * 
	 * @PathVariable --- 获取url路径上的参数
	 */
	@RequestMapping("/cities/{currentPage}/{pageSize}")
	@ResponseBody
	PageInfo<City> getCitiesByPage(@PathVariable int currentPage, @PathVariable int pageSize) {
		return testService.getCitiesByPage(currentPage, pageSize);
	}
	
	/**
	 * 删除城市
	 */
	@DeleteMapping(value="/city/{cityId}")
	@ResponseBody
	public void deleteCity(@PathVariable int cityId) {
		testService.deleteCity(cityId);
	}
	
	/**
	 * 更改城市
	 * 接受form表单数据 ---- application/x-www-form-urlencoded || @ModelAttribute
	 */
	@PutMapping(value="/city", consumes="application/x-www-form-urlencoded")
	@ResponseBody
	public City updateCity(@ModelAttribute City city) {
		return testService.updateCity(city);
	}
	
	/**
	 * 插入城市
	 * 接受json数据 ---- @RequestBody || application/json
	 */
	@PostMapping(value="/city", consumes="application/json")
	@ResponseBody
	public City insertCity(@RequestBody City city) {
		return testService.insertCity(city);
	}
	
	
	
	//******************************************//
	/**
	 * <p>
	 * 获取配置文件
	 * </p>
	 * 
	 * @author zm
	 * @Date 2019年11月26日
	 * @return
	 */
	@RequestMapping("/getConfig")
	@ResponseBody
	public String getConfig() {
		StringBuffer sb = new StringBuffer();
		sb.append(port).append("----").append(name).append("<br>").append("----").append(age).append("<br>")
				.append("----").append(description).append("<br>").append("----").append(random).append("<br>");

		sb.append(configTestBean.getName()).append("‐‐‐‐").append(configTestBean.getAge()).append("<br>").append("‐‐‐‐")
				.append("<br>").append(configTestBean.getDescription()).append("<br>").append("‐‐‐‐").append("<br>")
				.append(configTestBean.getRandom()).append("</br>");

		return sb.toString();
	}

	@RequestMapping("/info")
	@ResponseBody
	public String TestInfo(HttpServletRequest request, 
			@RequestParam(name="key", defaultValue="1111", required=false) String value) {
		
		String value2 = request.getParameter("key");
		return "This is TestInfo" + value + "---" + value2;
	}

	/**
	 * <p>
	 * 日志文件logback级别测试
	 * </p>
	 * 
	 * @author zm
	 * @Date 2019年11月26日
	 * @return
	 */
	@RequestMapping("/log")
	@ResponseBody
	public String loggerTest() {

		LOGGER.trace("This is trace log");
		LOGGER.trace("33333This is trace log");
		LOGGER.debug("This is debug log");
		LOGGER.info("This is info log");
		LOGGER.warn("This is warn log");
		LOGGER.error("This is error log");

		return "This is logger test.";
	}

	/**
	 * <p>
	 * 一个post接口
	 * </p>
	 * 
	 * @author zm
	 * @Date 2019年11月27日
	 * @return
	 */
	@PostMapping("/post")
	@ResponseBody
	public String postTest() {
		return "this is a post interface";
	}

}
