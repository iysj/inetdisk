package com.xiaoqee.hadoop.service.impl;

import com.xiaoqee.hadoop.mapper.HfileMapper;
import com.xiaoqee.hadoop.model.Hfile;
import com.xiaoqee.hadoop.service.HdfsFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HdfsFileServiceImpl implements HdfsFileService {
    
    @Autowired
    HfileMapper hfileMapper;
    
    
    @Override
    public List<Hfile> getFileByPid(String pid) {
	// TODO Auto-generated method stub
	return hfileMapper.getFileByPid(pid);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
	// TODO Auto-generated method stub
	return hfileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Hfile record) {
	// TODO Auto-generated method stub
	return hfileMapper.insert(record);
    }

    @Override
    public Hfile selectByPrimaryKey(String id) {
	// TODO Auto-generated method stub
	return hfileMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Hfile record) {
	// TODO Auto-generated method stub
	return hfileMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Hfile> findList(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer findTotal(Map<String, Object> param) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Hfile> getUserFileTree(String uid, String pid,String ftype) {
	if(StringUtils.isNotEmpty(ftype)){
	    return hfileMapper.getUserFileTree(uid, pid,ftype);
	}else{
	    return hfileMapper.getUserFileTree(uid, pid,null);
	}
	
    }

    @Override
    public List<Hfile> getFileByMd5(String md5) {
	// TODO Auto-generated method stub
	return hfileMapper.getFileByMd5(md5);
    }

}
