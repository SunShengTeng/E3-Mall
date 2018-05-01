package cn.sst.e3mall.cart.service;

import java.util.List;

import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.pojo.TbItem;

public interface CartService {

	/**
	 * 添加商品到购物车
	 * @param itemId
	 * @param num
	 * @return
	 */
	E3Result addCart(Long userId,Long itemId,int num);
	/**
	 * 取出购物车列表
	 * @param userId
	 * @return
	 */
	List<TbItem> getCartList(Long userId);
	/**
	 * 修改购物车中商品的数据
	 * @param userId
	 * @param itemId
	 * @return
	 */
	E3Result updateCartItemNumber(Long userId,Long itemId,int num);
	/**
	 * 从用户购物车中删除商品
	 * @param userId
	 * @param itemId
	 * @return
	 */
	E3Result deleteItemFromCart(Long userId,Long itemId);
	/**
	 * 合并缓存和服务端的购物车列表
	 * @param cookieCartList
	 * @param userId
	 * @return
	 */
	List<TbItem> mergeCartList(List<TbItem> cookieCartList,Long userId);
}
