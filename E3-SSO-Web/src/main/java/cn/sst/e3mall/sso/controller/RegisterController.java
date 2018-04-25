package cn.sst.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.pojo.TbUser;
import cn.sst.e3mall.sso.service.RegisterService;

@Controller
public class RegisterController {

	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("/register")
	public String pageController(){
		return "register";
	}
	
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	public E3Result registerUser(TbUser user){
		E3Result result = registerService.registerUser(user);
		return result;
	}
}
