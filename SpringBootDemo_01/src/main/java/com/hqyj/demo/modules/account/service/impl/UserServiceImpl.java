package com.hqyj.demo.modules.account.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.demo.modules.account.dao.UserDao;
import com.hqyj.demo.modules.account.entity.User;
import com.hqyj.demo.modules.account.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}
	
	@Override
	public List<User> getUsers() {
		return Optional.ofNullable(userDao.getUsers())
				.orElse(Collections.emptyList());
	}
	
	@Override
	public PageInfo<User> getUsersByPage(int currentPage, int pageSize) {
		PageHelper.startPage(currentPage,pageSize);
		List<User> users = Optional.ofNullable(userDao.getUsersByPage())
				.orElse(Collections.emptyList());
		return new PageInfo<>(users);
	}

	@Override
	public User insertUser(User user) {
		userDao.insertUser(user);
		return user;
	}

	@Override
	public User updateUser(User user) {
		userDao.updateUser(user);
		return user;
	}

	@Override
	public void deleteUser(int userId) {
		userDao.deleteUser(userId);
	}

	

	
}
