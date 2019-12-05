package com.hqyj.demo.modules.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.hqyj.demo.modules.account.entity.User;
import com.hqyj.demo.modules.test.entity.City;

@Repository
@Mapper
public interface UserDao {
	
	
	/**
	 * 
	 * 根据ID查询用户
	 */
	@Select("select * from m_user where user_id=#{userId}")
	User getUserById(int userId);
	
	/**
	 * 
	 * 查询所有用户
	 */
	@Select("select * from m_user")
	List<User> getUsers();
	
	/**
	 * 
	 * 分页查询用户
	 */
	@Select("select * from m_user")
	List<User> getUsersByPage();
	
	/**
	 * 插入用户
	 * useGeneratedKeys：包装插入id
	 */
	@Insert("insert into m_user (user_name, password, create_date) "
			+ "values (#{userName}, #{password}, #{createDate})")
	@Options(useGeneratedKeys=true, keyColumn="user_id", keyProperty="userId")
	void insertUser(User user);
	
	/**
	 * 更改用户信息
	 */
	@Update("update m_user set password = #{password} where user_id = #{userId}")
	void updateUser(User user);
	
	/**
	 * 删除用户信息
	 */
	@Delete("delete from m_user where user_id = #{userId}")
	void deleteUser(int userId);
	
}
