package com.kingsj.hadoop.service;

import java.util.Date;
import java.util.UUID;

import com.kingsj.hadoop.common.MyWebUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.kingsj.hadoop.model.User;



@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class }) 
@ContextConfiguration(locations = { "classpath:applicationContext*.xml" })
///**txManage是配置的事物管理Object  defaultRollback = true 任何情况都回滚*/
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class UserServiceTest {
    @Autowired
    private UserService userService;
    
    @Test
    public void login(){
	String username =RandomStringUtils.randomAlphanumeric(10);
	User user = new User();
	String salt = UUID.randomUUID().toString();
	user.setSalt(salt);
	user.setAddtime(new Date());
	user.setStatus(1);
	user.setUsername(username);
	String pwd = MyWebUtil.getHashPWD(salt,"zengjr");
	user.setPassword(pwd);
	userService.insert(user);
	
	User logUser = userService.selectByUserName(username);
	userService.deleteByPrimaryKey(logUser.getUserid());
	Assert.assertNotNull(logUser);
	
	
    }
}
