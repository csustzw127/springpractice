package com.zw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zw.dynamic.datasource.annotation.DynamicDataSource;
import com.zw.mapper.business.UserMapper;
import com.zw.vo.User;

@DynamicDataSource(dataSource="master")
public class UserServiceImpl {
	@Autowired
	private UserMapper userMapper;
	
	public User findUser(int id) {
		return userMapper.queryUserById(id);
	}
	
	public void addUser(User user) {
		userMapper.addUser(user);
	}
}
