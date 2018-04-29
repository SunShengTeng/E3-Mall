package cn.sst.e3mall.cart.service;

import cn.sst.e3mall.common.Utils.E3Result;

public interface CartService {

	/**
	 * 添加商品到购物车
	 * @param itemId
	 * @param num
	 * @return
	 */
	E3Result addCart(Long itemId,int num);
	E3Result addCart(Long itemId,int num,Long itemSpecId);
	
}
