package com.kingsj.hadoop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingsj.hadoop.mapper.UserMapper;
import com.kingsj.hadoop.model.User;
import com.kingsj.hadoop.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserMapper userMapper;
    
    @Override
    public int deleteByPrimaryKey(String id) {
	return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User selectByUserName(String userName) {
	User u =  userMapper.selectByUserName(userName);
	return u;
    }

    @Override
    public User login(String username, String pwd) {
	
	return userMapper.selectByUser(username, pwd);
    }

    @Override
    public int insert(User record) {
	return userMapper.insert(record);
    }

    @Override
    public User selectByPrimaryKey(String id) {
	return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(User record) {
	return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<User> findList(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer findTotal(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return null;
    }

}
