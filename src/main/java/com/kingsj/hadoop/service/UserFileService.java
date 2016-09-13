package com.kingsj.hadoop.service;

import com.kingsj.hadoop.model.UserFile;

public interface UserFileService extends BaseService<UserFile, String>{
    
    UserFile selectUserFileByFid(String uid,String fid);
    
    
}
