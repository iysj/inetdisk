package com.xiaoqee.hadoop.common;

import org.apache.shiro.crypto.hash.Sha256Hash;

public class MyWebUtil {

    public static String getHashPWD(String salt, String pwd) {
	return new Sha256Hash(pwd, salt).toBase64();
    }
}
