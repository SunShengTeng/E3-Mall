package cn.sst.e3mall.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
/**
 * Search全局异常处理
 * @author sunshengteng
 *
 */
public class SearchGlobalExceptionReslover implements HandlerExceptionResolver {

	Logger logger = LoggerFactory.getLogger(SearchGlobalExceptionReslover.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView modelAndView = new ModelAndView();

		// 写日志文件
		logger.error("系统发生异常", ex);
		// 发邮件、发短信
		// Jmail：可以查找相关的资料
		// 需要在购买短信。调用第三方接口即可。
		// 展示错误页面
		modelAndView.addObject("message", "系统发生异常，请稍后重试");
		modelAndView.setViewName("error/exception");

		return modelAndView;
	}

}
