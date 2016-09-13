package com.kingsj.hadoop.model;

import java.io.Serializable;

public class UserParam implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2053413040600828354L;

    private Integer id;

    private String userid;

    private String paramkey;

    private String paramval;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getParamkey() {
        return paramkey;
    }

    public void setParamkey(String paramkey) {
        this.paramkey = paramkey;
    }

    public String getParamval() {
        return paramval;
    }

    public void setParamval(String paramval) {
        this.paramval = paramval;
    }
}