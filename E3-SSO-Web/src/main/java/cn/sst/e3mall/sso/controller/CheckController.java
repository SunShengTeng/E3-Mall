package cn.sst.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.sso.service.CheckService;

@Controller
public class CheckController {

	@Autowired
	private CheckService checkService;

	/**
	 * 根据用户名/电话/邮箱校验用户是否存在
	 * 
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/user/check/{checkData}/{type}")
	@ResponseBody
	public E3Result checkUserIsExist(@PathVariable String checkData, @PathVariable Integer type, String username) {
		if (type == 4) {
			return checkService.checkUserIsExist(username, 1);
		}
		E3Result e3Result = checkService.checkUserIsExist(checkData, type);
		return e3Result;
	}

	@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public Object checkUserIsLogin(@PathVariable String token,String callback) {
		// 1、校验登陆状态
		E3Result e3Result = checkService.checkUserIsLogin(token);
		// 2、检查是否是jsonp请求
		if (!StringUtils.isBlank(callback)) {
			//把结果封装成一个js语句响应
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(e3Result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return e3Result;
	}

	
}
