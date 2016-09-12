package com.xiaoqee.hadoop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaoqee.hadoop.common.MyWebConstant;
import com.xiaoqee.hadoop.model.User;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
    
    Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	User user = (User)request.getSession().getAttribute(MyWebConstant.USER_CURRENT_LOGIN);
	if(user == null){
	    request.getRequestDispatcher("/showlogin").forward(request, response);
	    return false;
	}
	return super.preHandle(request, response, handler);
    }
    
}
