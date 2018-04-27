package cn.sst.e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.sst.e3mall.common.Jedis.JedisClient;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.common.Utils.JsonUtils;
import cn.sst.e3mall.mapper.TbUserMapper;
import cn.sst.e3mall.pojo.TbUser;
import cn.sst.e3mall.pojo.TbUserExample;
import cn.sst.e3mall.pojo.TbUserExample.Criteria;
import cn.sst.e3mall.sso.service.LoginService;
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper tbUserMapper;
	
	@Autowired
	private JedisClient jedisCluster;
	
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result loginByUsernamePassword(String username, String password) {
		// 1、根据用户名查询用户信息
		TbUserExample userExample = new TbUserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> userList = tbUserMapper.selectByExample(userExample);
		if (userList == null || userList.size() == 0) {
			return E3Result.build(400, "用户不存在");
		}
		// 2、判断密码是否正确
		TbUser user = userList.get(0);
		if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
			return E3Result.build(400, "用户密码错误");
		}
		// 3、生成用户token，并将用户登陆信息写入redis库
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		jedisCluster.set("SESSION:"+token, JsonUtils.objectToJson(user));
		jedisCluster.expire("SESSION:"+token, SESSION_EXPIRE);// 设置session过期时间
		// 4、封装结果集
		return E3Result.ok(token);
	}

	@Override
	public void logout(String token) {
		// 删除redis中的key
		if (StringUtils.isNotBlank(token)) {
			try {
				jedisCluster.del("SESSION:"+token);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
