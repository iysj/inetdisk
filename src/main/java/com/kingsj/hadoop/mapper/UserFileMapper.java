package com.kingsj.hadoop.mapper;

import org.apache.ibatis.annotations.Param;

import com.kingsj.hadoop.model.UserFile;

public interface UserFileMapper extends SqlMapper,BaseMapper<UserFile, String>{
    
    public UserFile selectUserFileByFid(@Param("uid") String uid, @Param("fid") String fid);
    
}