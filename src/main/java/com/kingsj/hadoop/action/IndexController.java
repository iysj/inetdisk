package com.kingsj.hadoop.action;

import com.kingsj.hadoop.common.MyWebConstant;
import com.kingsj.hadoop.common.MyWebUtil;
import com.kingsj.hadoop.model.User;
import com.kingsj.hadoop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes(MyWebConstant.USER_CURRENT_LOGIN)
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
	UserService userService;
    
    @RequestMapping(value = "/showlogin")
    public String showlogin(HttpServletRequest request) {
	Object obj = request.getSession().getAttribute(MyWebConstant.USER_CURRENT_LOGIN);
	if(null != obj){
	    return "index";
	}else{
	    return "login";
	}
	
    }
    @RequestMapping(value ={ "/index",""})
    public String index() {
	return "redirect:/showlogin";
    }
    
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
	request.getSession().removeAttribute(MyWebConstant.USER_CURRENT_LOGIN);
	return "redirect:/showlogin";
    }
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password, Model model) {
	logger.info(username + "---login---" + System.currentTimeMillis());
	Map<String, Object> resultMap = new HashMap<String, Object>();
	resultMap.put("status", -1);
	resultMap.put("msg", "登录失败");
	
	User logUser = userService.selectByUserName(username);
	if (logUser != null) {
	    String pwd = MyWebUtil.getHashPWD(logUser.getSalt(), password);
	    if (pwd.equals(logUser.getPassword())) {
		logger.info(username + "---loginSuccess---" + System.currentTimeMillis());
		model.addAttribute("user", logUser);
		resultMap.put("status", 0);
		resultMap.put("msg", "登录成功");

		model.addAttribute(MyWebConstant.USER_CURRENT_LOGIN, logUser);
	    }
	}
	model.addAttribute("result", resultMap);
	if((Integer)resultMap.get("status") == 0){
	    return "index";
	}else{
	    return "login";
	}
	
    }

    public String mkdir() {
	return "";
    }
}
