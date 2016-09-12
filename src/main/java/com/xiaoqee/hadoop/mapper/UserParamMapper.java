package com.xiaoqee.hadoop.mapper;

import org.apache.ibatis.annotations.Param;

import com.xiaoqee.hadoop.model.UserParam;

public interface UserParamMapper extends SqlMapper,BaseMapper<UserParam, String>{
    
    UserParam selectByParamKey(@Param("userid") String userid,@Param("paramKey") String paramKey);
    
}