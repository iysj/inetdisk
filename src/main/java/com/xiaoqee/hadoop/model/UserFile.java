package com.xiaoqee.hadoop.model;

import java.io.Serializable;
import java.util.Date;

public class UserFile implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5113730750075071758L;

    private String id;

    private String userid;

    private String fid;

    private Date addtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}