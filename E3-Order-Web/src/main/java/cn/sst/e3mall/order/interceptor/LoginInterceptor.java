package cn.sst.e3mall.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.sst.e3mall.common.Utils.CookieUtils;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.pojo.TbUser;
import cn.sst.e3mall.sso.service.CheckService;

public class LoginInterceptor implements HandlerInterceptor {

	@Value("${COOKIENAME_TOKEN}")
	private String COOKIENAME_TOKEN;
	
	@Value("${SSO_URL}")
	private String SSO_URL;
	
	@Autowired
	private CheckService checkService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 取token
		String cookieValue = CookieUtils.getCookieValue(request, COOKIENAME_TOKEN);
		if (!StringUtils.isNotBlank(cookieValue)) {
			// 返回登陆页面
			//如果token不存在，未登录状态，跳转到sso系统的登录页面。用户登录成功后，跳转到当前请求的url
			response.sendRedirect(SSO_URL + "/page/login?redirectUrl=" + request.getRequestURL());
			//拦截
			return false;
		}
		
		E3Result e3Result = checkService.checkUserIsLogin(cookieValue);
		if (e3Result.getStatus() != 200) {
			// 返回登陆页面
			//如果token不存在，未登录状态，跳转到sso系统的登录页面。用户登录成功后，跳转到当前请求的url
			response.sendRedirect(SSO_URL + "/page/login?redirectUrl=" + request.getRequestURL());
			//拦截
			return false;
		}
		// 取用户信息
		TbUser user = (TbUser) e3Result.getData();
		request.setAttribute("user", user);
		return true;
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		

	}

}
