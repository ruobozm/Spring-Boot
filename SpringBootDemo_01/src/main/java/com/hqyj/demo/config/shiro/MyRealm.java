package com.hqyj.demo.config.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hqyj.demo.modules.account.entity.Resource;
import com.hqyj.demo.modules.account.entity.Role;
import com.hqyj.demo.modules.account.entity.User;
import com.hqyj.demo.modules.account.service.AccountService;

/**
 * shiro核心组件之一：realm。继承 AuthorizingRealm 类，重写身份验证和资源授权两个方法
 */
@Component
public class MyRealm extends AuthorizingRealm {
	@Autowired
	private AccountService accountService;

	/*
	 * 资源授权，包装资源授权验证器，将当前用户对应的角色信息，以及角色所拥有的资源信息放入验证器
	 * 通过前端页面shiro标签、控制器方法shiro注解与验证器比对，实现用户资源授权功能
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		String userName = (String) principals.getPrimaryPrincipal();
		User user = accountService.getUserByName(userName);
		if (user == null) {
			return null;
		}

		List<Role> roles = accountService.getRolesByUserId(user.getUserId());
		for (Role role : roles) {
			authorizationInfo.addRole(role.getRoleName());
			List<Resource> resources = accountService.getResourcesByRoleId(role.getRoleId());
			for (Resource resource : resources) {
				authorizationInfo.addStringPermission(resource.getPermission());
			}
		}

		return authorizationInfo;
	}

	/*
	 * 包装身份验证器，将数据库用户名和密码放入验证器 通过登录框输入的用户名和密码与验证器做比对，实现身份验证功能
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		User user = accountService.getUserByName(userName);
		if (user == null) {
			throw new UnknownAccountException("The account do not exist.");
		}

		return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
	}

}
