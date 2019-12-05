package com.hqyj.demo.modules.account.commom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	@RequestMapping("/403")
	public String errorPageForUnauthorized(ModelMap modelMap) {
		return "index";
	}
}
