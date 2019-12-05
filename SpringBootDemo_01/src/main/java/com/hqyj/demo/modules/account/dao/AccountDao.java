package com.hqyj.demo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.hqyj.demo.modules.account.entity.Resource;
import com.hqyj.demo.modules.account.entity.Role;
import com.hqyj.demo.modules.account.entity.User;

@Repository
@Mapper
public interface AccountDao {
	
	@Select("select * from m_user where user_name = #{userName} and password = #{password}")
	User getUserByUserNameAndPassword(User user);
	
	@Select("select * from m_user ")
	List<User> getUsers();
	
	@Delete("delete from m_user where user_id = #{userId}")
	int deleteUser(int userId);
	
	@Select("select * from m_user where user_name = #{userName}")
	User getUserByName(String userName);
	
	@Insert("insert m_user(user_name, password, create_date) "
			+ "value(#{userName}, #{password}, #{createDate})")
	@Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
	void addUser(User user);

	@Select("select * from m_role")
	List<Role> getRoles();

	@Select("select * from m_role role left join m_user_role userRole "
			+ "on role.role_id = userRole.role_id where userRole.user_id = #{userId}")
	List<Role> getRolesByUserId(int userId);
	
	@Delete("delete from m_user_role where user_id = #{userId}")
	void deletUserRoleByUserId(int userId);
	
	@Insert("insert m_user_role(role_id, user_id) value(#{roleId}, #{userId})")
	void addUserRole(int roleId, int userId);

	@Update("update m_role set role_name = #{roleName} where role_id = #{roleId}")
	void updateRole(Role role);
	
	@Insert("insert m_role(role_name) value(#{roleName})")
	@Options(useGeneratedKeys=true, keyProperty="roleId", keyColumn="role_id")
	void addRole(Role role);
	
	@Delete("delete from m_role where role_id = #{roleId}")
	void deleteRole(int roleId);
	
	@Select("select * from m_resource")
	List<Resource> getResources();

	@Select("select * from m_role role left join m_role_resource roleResource "
			+ "on role.role_id = roleResource.role_id where roleResource.resource_id = #{resourceId}")
	List<Role> getRolesByResourceId(int resourceId);
	
	@Update("update m_resource set resource_name = #{resourceName}, resource_uri = #{resourceUri}, "
			+ "permission=#{permission} where resource_id=#{resourceId}")
	void updateResource(Resource resource);
	
	@Insert("insert m_resource(resource_name, resource_uri, permission) value(#{resourceName}, "
			+ "#{resourceUri}, #{permission})")
	@Options(useGeneratedKeys = true, keyProperty = "resourceId", keyColumn = "resource_id")
	void addResource(Resource resource);
	
	@Delete("delete from m_role_resource where resource_id = #{resourceId}")
	void deletRoleResourceByResourceId(int resourceId);
	
	@Insert("insert m_role_resource(role_id, resource_id) value(#{roleId}, #{resourceId})")
	void addRoleResource(int roleId, int resourceId);
	
	@Delete("delete from m_resource where resource_id = #{resourceId}")
	void deleteResource(int resourceId);

	@Select("select * from m_resource resource left join m_role_resource roleResource on "
			+ "resource.resource_id = roleResource.resource_id where roleResource.role_id = #{roleId}")
	List<Resource> getResourcesByRoleId(int roleId);

}
