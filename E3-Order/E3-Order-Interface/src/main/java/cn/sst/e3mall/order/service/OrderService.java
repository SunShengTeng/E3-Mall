package cn.sst.e3mall.order.service;

import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.order.pojo.OrderInfo;

public interface OrderService {
	/**
	 * 创建订单数据
	 * @param orderInfo
	 * @return
	 */
	E3Result createOrder(OrderInfo orderInfo);
}
