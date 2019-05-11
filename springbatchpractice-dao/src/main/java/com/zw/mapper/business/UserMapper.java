package com.zw.mapper.business;

import org.apache.ibatis.annotations.Param;

import com.zw.vo.User;

public interface UserMapper {

	User queryUserById(@Param("id")int id);
	
	void addUser(@Param("user") User user);
}
