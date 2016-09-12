package com.xiaoqee.hadoop.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesUtil {

    private static final Log logger = LogFactory.getLog(PropertiesUtil.class);

    /**
     * 
     * @param fileName
     *            文件名
     * @param key
     *            key
     * @param defaultValue
     *            NULL的时候默认返回值
     * @return
     */
    public static Object getPropertyFromConfiguration(String fileName, String key, Object defaultValue) {

	InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
	Properties p = new Properties();
	try {
	    p.load(inputStream);
	} catch (IOException e) {
	    logger.error("{}", e);
	} finally {
	    try {
		inputStream.close();
	    } catch (IOException e) {
		logger.error("{}", e);
	    }
	}
	return p.getProperty(key) == null ? defaultValue : p.getProperty(key);
    }

    public static Properties loadConfiguration(String fileName) {
	InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
	Properties p = new Properties();
	try {
	    p.load(inputStream);
	} catch (IOException e) {
	    logger.error("{}", e);
	} finally {
	    try {
		inputStream.close();
	    } catch (IOException e) {
		logger.error("{}", e);
	    }
	}

	return p;
    }

}
