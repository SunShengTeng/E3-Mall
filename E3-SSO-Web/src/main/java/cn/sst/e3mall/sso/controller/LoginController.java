package cn.sst.e3mall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * e3mall:登陆
 * @author sunshengteng
 *
 */
@Controller
public class LoginController {

	@RequestMapping("/page/login")
	public String pageController(){
		return "login";
	}
}
