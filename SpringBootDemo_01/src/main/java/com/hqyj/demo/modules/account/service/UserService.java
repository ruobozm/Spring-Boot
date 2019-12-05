package com.hqyj.demo.modules.account.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.hqyj.demo.modules.account.entity.User;

public interface UserService {
	
	/**
	 * 查询用户
	 */
	User getUserById(int userId);
	
	/**
	 * 查询所有用户
	 */
	List<User> getUsers();
	
	/**
	 * 分页查询用户信息
	 */
	PageInfo<User> getUsersByPage(int currentPage, int pageSize);
	
	/**
	 * 插入用户
	 */
	User insertUser(User user);
	
	/**
	 * 更改用户信息
	 */
	User updateUser(User user);
	
	/**
	 * 删除用户
	 */
	void deleteUser(int userId);
	
	
}
