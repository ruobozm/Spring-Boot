package com.hqyj.demo.modules.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.hqyj.demo.modules.account.entity.User;
import com.hqyj.demo.modules.account.service.UserService;
import com.hqyj.demo.modules.test.entity.City;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
		
		@RequestMapping("/getUser/{userId}")
		@ResponseBody
		public User getUserById(@PathVariable int userId) {
			return userService.getUserById(userId);
		}
		
		@RequestMapping("getUsers")
		@ResponseBody
		public List<User> getUsers(){
			return userService.getUsers();
		}
		
		/**
		 * 分页查询用户信息
		 * 
		 * @PathVariable --- 获取url路径上的参数
		 */
		@RequestMapping("/getUsers/{currentPage}/{pageSize}")
		@ResponseBody
		PageInfo<User> getUsersByPage(@PathVariable int currentPage, @PathVariable int pageSize) {
			return userService.getUsersByPage(currentPage, pageSize);
		}
		
		/**
		 * 插入用户
		 * 接受json数据 ---- @RequestBody || application/json
		 */
		@PostMapping(value="/insertUser", consumes="application/json")
		@ResponseBody
		public User insertUser(@RequestBody User user) {
			return userService.insertUser(user);
		}
		

		/**
		 * 更改用户
		 * 接受form表单数据 ---- application/x-www-form-urlencoded || @ModelAttribute
		 */
		@PutMapping(value="/updateUser", consumes = "application/x-www-form-urlencoded")
		@ResponseBody
		public User updateUser(@ModelAttribute User user) {
			return userService.updateUser(user);
		}
		
		/**
		 * 删除用户
		 */
		@DeleteMapping("/deleteUser/{userId}")
		@ResponseBody
		public void deleteUser(@PathVariable int userId) {
			userService.deleteUser(userId);
		}
}
