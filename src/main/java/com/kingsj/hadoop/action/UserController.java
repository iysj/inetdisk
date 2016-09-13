package com.kingsj.hadoop.action;

import com.kingsj.hadoop.common.MyWebConstant;
import com.kingsj.hadoop.common.MyWebUtil;
import com.kingsj.hadoop.model.User;
import com.kingsj.hadoop.model.UserParam;
import com.kingsj.hadoop.service.UserParamService;
import com.kingsj.hadoop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes(MyWebConstant.USER_CURRENT_LOGIN)
public class UserController {
    
    @Autowired
	UserService userService;
    
    @Autowired
    UserParamService userParamService;
    
    @RequestMapping(value = {"/user/editpwd" })
    @ResponseBody
    public Map<String,Object> editpwd(@RequestParam(value = "newpass", required = true) String newpass,
	    @RequestParam(value = "oldpwd", required = true) String oldpwd,
	    Model model, @ModelAttribute(MyWebConstant.USER_CURRENT_LOGIN) User user) {
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	resultMap.put("status", -1);
	resultMap.put("msg", "修改失败");
	
	 String pwd = MyWebUtil.getHashPWD(user.getSalt(), oldpwd);
	 if(pwd.equals(user.getPassword())){
	     String newpwd = MyWebUtil.getHashPWD(user.getSalt(), newpass);
	     user.setPassword(newpwd);
	     userService.updateByPrimaryKey(user);
	     
	     resultMap.put("status", 0);
	     resultMap.put("msg", "修改成功");
	 }
	return resultMap;
    }
    
    @RequestMapping(value = {"/user/building" })
    public String building() {
	return "building";
    }
    @RequestMapping(value = {"/user/setdlpath" })
    public String setpath(@RequestParam(value = "path", required = true) String path,
	    Model model, @ModelAttribute(MyWebConstant.USER_CURRENT_LOGIN) User user) {
	
	UserParam userParam = userParamService.selectByParamKey(user.getUserid(), MyWebConstant.USER_DEFAULT_SAVE_PATH);
	if(userParam == null){
	    userParam = new UserParam();
	    userParam.setUserid(user.getUserid());
	    userParam.setParamkey(MyWebConstant.USER_DEFAULT_SAVE_PATH);
	    userParam.setParamval(path);
	    userParamService.insert(userParam);
	}else{
	    userParam.setParamval(path);
	    userParamService.updateByPrimaryKey(userParam);
	}
	
	return "savepath";
    }
    @RequestMapping(value = {"/user/savepath" })
    public String savepath(Model model, @ModelAttribute(MyWebConstant.USER_CURRENT_LOGIN) User user) {
	UserParam userParam = userParamService.selectByParamKey(user.getUserid(), MyWebConstant.USER_DEFAULT_SAVE_PATH);
	if(userParam != null){
	    model.addAttribute("path", userParam.getParamval());
	}else{
	    model.addAttribute("path", "d:\\");
	}
	
	return "savepath";
    }
}
