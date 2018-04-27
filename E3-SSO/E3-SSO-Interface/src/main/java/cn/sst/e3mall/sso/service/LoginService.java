package cn.sst.e3mall.sso.service;


import cn.sst.e3mall.common.Utils.E3Result;

public interface LoginService {

	/**
	 * 登陆
	 * @param username
	 * @param password
	 * @return
	 */
	E3Result loginByUsernamePassword(String username,String password);

	/**
	 * 注销
	 * @param token
	 */
	void logout(String token);
}
