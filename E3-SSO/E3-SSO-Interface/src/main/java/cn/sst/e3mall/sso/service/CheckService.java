package cn.sst.e3mall.sso.service;

import cn.sst.e3mall.common.Utils.E3Result;

public interface CheckService {

	/**
	 * 根据类型校验用户数据是否已经存在
	 * @param checkData
	 * @param type
	 * @return
	 */
	E3Result checkUserIsExist(String checkData,Integer type);
	/**
	 * 校验登陆状态
	 * @param token
	 * @return
	 */
	E3Result checkUserIsLogin(String token);
	
}
