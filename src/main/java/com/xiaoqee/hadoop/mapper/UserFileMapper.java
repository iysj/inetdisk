package com.xiaoqee.hadoop.mapper;

import org.apache.ibatis.annotations.Param;

import com.xiaoqee.hadoop.model.UserFile;

public interface UserFileMapper extends SqlMapper,BaseMapper<UserFile, String>{
    
    public UserFile selectUserFileByFid(@Param("uid") String uid, @Param("fid") String fid);
    
}