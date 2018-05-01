package cn.sst.e3mall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.sst.e3mall.cart.service.CartService;
import cn.sst.e3mall.common.Jedis.JedisClient;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.common.Utils.JsonUtils;
import cn.sst.e3mall.mapper.TbItemMapper;
import cn.sst.e3mall.pojo.TbItem;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private JedisClient jedisClient;

	@Value("${REDISNAME_CART}")
	private String REDISNAME_CART;

	@Autowired
	private TbItemMapper tbItemMapper;

	@Override
	public E3Result addCart(Long userId, Long itemId, int num) {
		// 取购物车列表
		Boolean hexists = jedisClient.hexists(REDISNAME_CART + userId, itemId + "");
		if (hexists) {
			// 如果购物车中有此商品，增加商品的购买数量
			String itemString = jedisClient.hget(REDISNAME_CART + userId, itemId + "");
			TbItem tbItem = JsonUtils.jsonToPojo(itemString, TbItem.class);
			tbItem.setNum(tbItem.getNum() + num);
			jedisClient.hset(REDISNAME_CART + userId, itemId + "", JsonUtils.objectToJson(tbItem));
		}
		// 购物车中没有此商品，重新添加
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		// 设置购物车数据量
		tbItem.setNum(num);
		// 取一张图片
		String image = tbItem.getImage();
		if (StringUtils.isNotBlank(image)) {
			tbItem.setImage(image.split(",")[0]);
		}
		jedisClient.hset(REDISNAME_CART + userId, itemId.toString(), JsonUtils.objectToJson(tbItem));
		return E3Result.ok();
	}

	@Override
	public List<TbItem> getCartList(Long userId) {
		ArrayList<TbItem> itemList = new ArrayList<>();
		// 判断是否有此用户的购物车数据
		Boolean exists = jedisClient.exists(REDISNAME_CART + userId);
		if (exists) {
			// 取出用户的购物车数据
			List<String> list = jedisClient.hvals(REDISNAME_CART + userId);
			for (String string : list) {
				TbItem tbItem = JsonUtils.jsonToPojo(string, TbItem.class);
				itemList.add(tbItem);
			}
		}
		return itemList;
	}

	@Override
	public E3Result updateCartItemNumber(Long userId, Long itemId, int num) {
		// 判断商品是否存在（存在则修改购买数量）
		Boolean hexists = jedisClient.hexists(REDISNAME_CART + userId, itemId + "");
		if (hexists) {
			String itemString = jedisClient.hget(REDISNAME_CART + userId, itemId + "");
			TbItem tbItem = JsonUtils.jsonToPojo(itemString, TbItem.class);
			tbItem.setNum(num);
			jedisClient.hset(REDISNAME_CART + userId, itemId + "", JsonUtils.objectToJson(tbItem));
		}
		return E3Result.ok();
	}

	@Override
	public E3Result deleteItemFromCart(Long userId, Long itemId) {
		// 判断商品是否存在（存在则修改购买数量）
		Boolean hexists = jedisClient.hexists(REDISNAME_CART + userId, itemId + "");
		if (hexists) {
			jedisClient.hdel(REDISNAME_CART + userId, itemId + "");
		}
		return E3Result.ok();
	}

	@Override
	public List<TbItem> mergeCartList(List<TbItem> cookieCartList, Long userId) {
		System.out.println(cookieCartList);
		// 将前端缓存的购物车合并到服务端
		for (TbItem tbItem : cookieCartList) {
			addCart(userId, tbItem.getId(), tbItem.getNum());
		}
		return getCartList(userId);
	}

}
