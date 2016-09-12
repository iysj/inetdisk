package com.xiaoqee.hadoop.service;

import com.xiaoqee.hadoop.model.UserParam;

public interface UserParamService extends BaseService<UserParam, String>{
    
    UserParam selectByParamKey(String userid,String paramKey);
    
}
