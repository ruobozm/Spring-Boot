package com.hqyj.demo.modules.account.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqyj.demo.modules.account.common.vo.Result;
import com.hqyj.demo.modules.account.entity.Resource;
import com.hqyj.demo.modules.account.entity.Role;
import com.hqyj.demo.modules.account.entity.User;
import com.hqyj.demo.modules.account.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/dashboard")
	public String dashboard() {
		return "index";
	}
	@RequestMapping("/login")
	public String LoginPage() {
		return "indexSimple";
	}
	
	/**
	 * 用户登录
	 */
	@PostMapping(value = "/doLogin", consumes = "application/json")
	@ResponseBody
	public Result doLogin(@RequestBody User user) {
		return accountService.getUserByUserNameAndPassword(user);
	}
	
	/**
	 * 用户退出登录
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, ModelMap modelMap) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/account/login";
	}
	
	/**
	 * 跳转注册页面
	 */
	@RequestMapping("/register")
	public String registerPage(ModelMap modelMap) {
		return "indexSimple";
	}
	
	/**
	 * 新增用户
	 */
	@RequestMapping(value="/doRegister", 
			method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Result doRegister(HttpServletRequest request, @RequestBody User user) {
		Result result = accountService.addUser(user);
		
		//存入Session
    	HttpSession session = request.getSession(true);
    	session.setAttribute("user", user);
    	
		return result;
	}
	
	/**
	 * 跳转user界面
	 */
	@RequestMapping("/users")
	@RequiresRoles(value={"admin", "manager"}, logical=Logical.OR)
	public String users(ModelMap modelMap){
		List<User> users = accountService.getUsers();
		List<Role> roles = accountService.getRoles();
		modelMap.put("users",users);
		modelMap.put("roles",roles);
		return "index";
	}
	
	/**
	 * 删除user
	 * shiro常见注解
	 * @RequiresAuthentication : 表示当前 Subject 已经认证登录的用户才能调用的代码块。
	 * @RequiresUser : 表示当前 Subject 已经身份验证或通过记住我登录的。
	 * @RequiresGuest : 表示当前 Subject 没有身份验证，即是游客身份。
	 * @RequiresRoles(value={"admin", "user"}, logical=Logical.AND)
	 * @RequiresPermissions (value={"***","***"}, logical= Logical.OR) 
	 */
	@RequestMapping("/deleteUser/{userId}")
	@RequiresPermissions("deleteUser")
	public String deleteUser(@PathVariable int userId) {
		accountService.deleteUser(userId);
		return "redirect:/account/users";
	}
	
	/**
	 * 编辑用户界面：根据用户Id查询已有的角色
	 */
	@RequestMapping("/roles/user/{userId}")
	@ResponseBody
	public List<Role> getRolesByUserId(@PathVariable("userId") int userId) {
		return accountService.getRolesByUserId(userId);
	}
	
	/**
	 * 编辑或者新增user
	 */
	@RequestMapping(value="/editUser", 
			method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Result editUser(@RequestBody User user) {
		System.err.println(user.getUserId());
		return accountService.editUser(user);
	}
	
	/**
	 * 跳转roles页面
	 */
	@RequestMapping("/roles")
	public String rolesPage(ModelMap modelMap) {
		modelMap.put("roles", accountService.getRoles());
		return "index";
	}
	
	/**
	 * 新增或修改role
	 */
	@RequestMapping(value="/editRole", 
			method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Result editRole(@RequestBody Role role) {
		return accountService.editRole(role);
	}
	
	/**
	 * 删除role
	 */
	@RequestMapping("/deleteRole/{roleId}")
	public String deleteRole(@PathVariable("roleId") int roleId) {
		accountService.deleteRole(roleId);;
		return "redirect:/account/roles";
	}
	
	/**
	 * 跳转resources页面
	 */
	@RequestMapping("/resources")
	public String resourcesPage(ModelMap modelMap) {
		modelMap.put("roles", accountService.getRoles());
		modelMap.put("resources", accountService.getResources());
		return "index";
	}
	
	/**
	 * 根据resourceId获取role list
	 */
	@RequestMapping("/roles/resource/{resourceId}")
	@ResponseBody
	public List<Role> getRolesByResourceId(@PathVariable("resourceId") int resourceId) {
		return accountService.getRolesByResourceId(resourceId);
	}
	
	/**
	 * 新增或编辑resource
	 */
	@RequestMapping(value="/editResource", 
			method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Result editResource(@RequestBody Resource resource) {
		return accountService.editResource(resource);
	}
	
	/**
	 * 删除resource
	 */
	@RequestMapping("/deleteResource/{resourceId}")
	public String deleteResource(@PathVariable("resourceId") int resourceId) {
		accountService.deleteResource(resourceId);
		return "redirect:/account/resources";
	}

}
