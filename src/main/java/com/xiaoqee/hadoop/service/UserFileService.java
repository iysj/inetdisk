package com.xiaoqee.hadoop.service;

import com.xiaoqee.hadoop.model.UserFile;

public interface UserFileService extends BaseService<UserFile, String>{
    
    UserFile selectUserFileByFid(String uid,String fid);
    
    
}
