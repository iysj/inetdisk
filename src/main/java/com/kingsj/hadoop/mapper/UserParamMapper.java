package com.kingsj.hadoop.mapper;

import org.apache.ibatis.annotations.Param;

import com.kingsj.hadoop.model.UserParam;

public interface UserParamMapper extends SqlMapper,BaseMapper<UserParam, String>{
    
    UserParam selectByParamKey(@Param("userid") String userid,@Param("paramKey") String paramKey);
    
}