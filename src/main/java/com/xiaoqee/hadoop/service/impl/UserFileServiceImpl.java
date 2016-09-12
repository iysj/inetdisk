package com.xiaoqee.hadoop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoqee.hadoop.mapper.UserFileMapper;
import com.xiaoqee.hadoop.model.UserFile;
import com.xiaoqee.hadoop.service.UserFileService;

@Service
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    UserFileMapper userFileMapper;
    
    
    @Override
    public UserFile selectUserFileByFid(String uid, String fid) {
	// TODO Auto-generated method stub
	return userFileMapper.selectUserFileByFid(uid,fid);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
	// TODO Auto-generated method stub
	return userFileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserFile record) {
	// TODO Auto-generated method stub
	return userFileMapper.insert(record);
    }

    @Override
    public UserFile selectByPrimaryKey(String id) {
	// TODO Auto-generated method stub
	return userFileMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(UserFile record) {
	// TODO Auto-generated method stub
	return userFileMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<UserFile> findList(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer findTotal(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return null;
    }

}
