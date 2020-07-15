package com.sdut.novel.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.sdut.novel.bean.User;
import com.sdut.novel.bean.UserInfo;


@Mapper
public interface UserDao {
	
	//注册
	@Insert("insert into users(account,password,username,email) values(#{user.account},#{user.password},#{user.account},#{user.email})")
	void addAccount(@Param("user")User user);
	
	//查找是否存在该账号
	@Select("select COUNT(account) from users where account=#{account}")
	int selectAccount(String account);
	
	//登录
	@Select("select password from users where account=#{account}")
	String login(String account);
	
	//获取用户信息
	@Select("select * from users where account=#{account}")
	User selectUser(String account);
	
	//修改信息
	@Insert("update users set password=#{user.newPassword},username=#{user.username},email=#{user.email},sex=#{user.sex},birthday=#{user.birthday},headPortrait=#{user.headPortrait} where account=#{user.account}")
	void updateUser(@Param("user")UserInfo user);
	
}
