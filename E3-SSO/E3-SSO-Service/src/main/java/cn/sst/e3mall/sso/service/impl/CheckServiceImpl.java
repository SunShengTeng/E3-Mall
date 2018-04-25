package cn.sst.e3mall.sso.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.mapper.TbUserMapper;
import cn.sst.e3mall.pojo.TbUser;
import cn.sst.e3mall.pojo.TbUserExample;
import cn.sst.e3mall.pojo.TbUserExample.Criteria;
import cn.sst.e3mall.sso.service.CheckService;

@Service
public class CheckServiceImpl implements CheckService {

	@Autowired
	private TbUserMapper tbUserMapper;
	
	@Override
	public E3Result checkUserIsExist(String checkData, Integer type) {
		TbUserExample userExample = new TbUserExample();
		Criteria criteria = userExample.createCriteria();
		//根据请求验证的类型，动态构建criteria(1、2、3分别代表username、phone、email)
		if (type == 1 && StringUtils.isNotBlank(checkData)) {
			criteria.andUsernameEqualTo(checkData);
		}else if (type == 2 && StringUtils.isNotBlank(checkData)) {
			criteria.andPhoneEqualTo(checkData);
		}else if (type == 2 && StringUtils.isNotBlank(checkData)) {
			criteria.andEmailEqualTo(checkData);
		}else {
			return E3Result.build(401, "type参数不合法");
		}
		// 验证用户数据是否已经存在
		List<TbUser> userList = tbUserMapper.selectByExample(userExample);
		if (userList !=null && userList.size() > 0) {
			// 存在此用户
			return E3Result.ok(false);
		}
		return E3Result.ok(true);
	}

}
