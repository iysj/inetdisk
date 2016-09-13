package com.kingsj.hadoop.service;

import com.kingsj.hadoop.model.User;

public interface UserService extends BaseService<User, String>{
    
    public User login(String username,String pwd);
    
    public User selectByUserName(String userName);
    
}
