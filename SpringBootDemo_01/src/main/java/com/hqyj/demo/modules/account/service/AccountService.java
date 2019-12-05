package com.hqyj.demo.modules.account.service;

import java.util.List;

import com.hqyj.demo.modules.account.common.vo.Result;
import com.hqyj.demo.modules.account.entity.Resource;
import com.hqyj.demo.modules.account.entity.Role;
import com.hqyj.demo.modules.account.entity.User;

public interface AccountService {
	Result getUserByUserNameAndPassword(User user);

	List<User> getUsers();

	Result deleteUser(int userId);

	Result addUser(User user);

	List<Role> getRoles();

	List<Role> getRolesByUserId(int userId);

	Result editUser(User user);

	Result editRole(Role role);

	void deleteRole(int roleId);

	List<Resource> getResources();

	List<Role> getRolesByResourceId(int resourceId);

	Result editResource(Resource resource);

	void deleteResource(int resourceId);

	User getUserByName(String userName);

	List<Resource> getResourcesByRoleId(int roleId);

}
