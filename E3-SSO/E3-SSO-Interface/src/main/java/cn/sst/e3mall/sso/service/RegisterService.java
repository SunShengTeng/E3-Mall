package cn.sst.e3mall.sso.service;

import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.pojo.TbUser;

public interface RegisterService {

	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	E3Result registerUser(TbUser user);
}
