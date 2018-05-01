package cn.sst.e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.sst.e3mall.common.Utils.CookieUtils;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.common.Utils.JsonUtils;
import cn.sst.e3mall.pojo.TbUser;
import cn.sst.e3mall.sso.service.CheckService;
/**
 * 登陆拦截器
 * @author sunshengteng
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Value("${COOKIENAME_TOKEN}")
	private String COOKIENAME_TOKEN;

	@Autowired
	private CheckService checkService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 判断是否登陆
		String token = CookieUtils.getCookieValue(request, COOKIENAME_TOKEN);
		if (StringUtils.isNotBlank(token)) {
			// 取token查询用户信息
			E3Result e3Result = checkService.checkUserIsLogin(token);
			// 验证token是否过期
			if (e3Result.getStatus() != 200) {
				return true;
			}
			TbUser user = (TbUser) e3Result.getData();
			// 将用户信息写入request
			request.setAttribute("user", user);
			return true;
		}
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
