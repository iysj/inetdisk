package com.kingsj.hadoop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kingsj.hadoop.model.Hfile;

public interface HfileMapper extends SqlMapper,BaseMapper<Hfile, String>{
    
    public List<Hfile> getUserFileTree(@Param("uid") String uid, @Param("pid") String pid,@Param("ftype") String ftype);
    
    public List<Hfile> getFileByMd5(@Param("fmd5") String md5);
    
    public List<Hfile> getFileByPid(@Param("pid") String pid);
    
}