package cn.sst.e3mall.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.sso.service.CheckService;

@Controller
@RequestMapping("/user")
public class CheckController {

	@Autowired
	private CheckService checkService;
	/**
	 * 根据用户名/电话/邮箱校验用户是否存在
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/check/{checkData}/{type}")
	@ResponseBody
	public E3Result checkUserIsExist(@PathVariable String checkData,@PathVariable Integer type,String username){
		if (type == 4) {
			return checkService.checkUserIsExist(username, 1);
		}
		E3Result e3Result = checkService.checkUserIsExist(checkData, type);
		return e3Result;
	}
	
}
