package com.kingsj.hadoop.service;

import com.kingsj.hadoop.model.UserParam;

public interface UserParamService extends BaseService<UserParam, String>{
    
    UserParam selectByParamKey(String userid,String paramKey);
    
}
