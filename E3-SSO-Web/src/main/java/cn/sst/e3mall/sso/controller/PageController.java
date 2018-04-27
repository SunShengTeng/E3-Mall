package cn.sst.e3mall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制
 * @author sunshengteng
 *
 */
@Controller
public class PageController {

	@RequestMapping("/page/login")
	public String loginPageController(){
		return "login";
	}
	
	@RequestMapping("/page/register")
	public String registerPageController(){
		return "register";
	}
}
