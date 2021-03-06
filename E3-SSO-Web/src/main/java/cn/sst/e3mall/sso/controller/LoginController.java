package cn.sst.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * e3mall:登陆
 * @author sunshengteng
 *
 */
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sst.e3mall.common.Utils.CookieUtils;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.sso.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	/**
	 * 登陆
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/user/login")
	@ResponseBody
	public E3Result loginByUsernamePassword(String redirectUrl, String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		// 1、登陆
		E3Result result = loginService.loginByUsernamePassword(username, password);
		// 2、登陆成功以后将token写入cookie
		if (result.getStatus() == 200) {
			CookieUtils.setCookie(request, response, "token", result.getData().toString());
		}
		// 3、登陆成功以后,写入需要跳转的目标页面
	    request.setAttribute("redirectUrl", redirectUrl);
		return result;
	}
	
	@RequestMapping("/user/logout")
	public String logout(HttpServletRequest request) {
	    // 1、删除redis中的token
		try {
			String token = CookieUtils.getCookieValue(request, "token");
			loginService.logout(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login";
	}
}
