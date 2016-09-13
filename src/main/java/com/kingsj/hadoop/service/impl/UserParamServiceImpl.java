package com.kingsj.hadoop.service.impl;

import java.util.List;
import java.util.Map;

import com.kingsj.hadoop.mapper.UserParamMapper;
import com.kingsj.hadoop.model.UserParam;
import com.kingsj.hadoop.service.UserParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserParamServiceImpl implements UserParamService {

    @Autowired
    UserParamMapper userParamMapper;
    
    @Override
    public UserParam selectByParamKey(String userid, String paramKey) {
	// TODO Auto-generated method stub
	return userParamMapper.selectByParamKey(userid, paramKey);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
	// TODO Auto-generated method stub
	return userParamMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserParam record) {
	// TODO Auto-generated method stub
	return userParamMapper.insert(record);
    }

    @Override
    public UserParam selectByPrimaryKey(String id) {
	// TODO Auto-generated method stub
	return userParamMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(UserParam record) {
	// TODO Auto-generated method stub
	return userParamMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<UserParam> findList(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer findTotal(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return null;
    }

}
