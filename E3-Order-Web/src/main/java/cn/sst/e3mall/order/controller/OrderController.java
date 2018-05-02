package cn.sst.e3mall.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sst.e3mall.cart.service.CartService;
import cn.sst.e3mall.common.Utils.CookieUtils;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.common.Utils.JsonUtils;
import cn.sst.e3mall.order.pojo.OrderInfo;
import cn.sst.e3mall.order.service.OrderService;
import cn.sst.e3mall.pojo.TbItem;
import cn.sst.e3mall.pojo.TbUser;

@Controller
public class OrderController {

	@Value("${COOKNAME_CART}")
	private String COOKNAME_CART;

	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;

	@RequestMapping("/order/order-cart")
	public String confirmOrderPage(HttpServletRequest request, Model model, HttpServletResponse response)
			throws Exception {
		// 判断用户是否登陆
		TbUser user = (TbUser) request.getAttribute("user");
		if (user == null)
			return "error";
		// 从cookie中取购物车数据
		List<TbItem> cartList = getCookieCartList(request);
		// 本地购物车跟后台的购物车合并
		List<TbItem> mergeCartList = cartService.mergeCartList(cartList, user.getId());
		// 封装结果集数据

		request.setAttribute("cartList", mergeCartList);
		// 返回逻辑视图
		return "order-cart";
	}

	@RequestMapping("/order/create")
	public String createOrder(OrderInfo orderInfo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 补全订单数据
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		
		// 生成订单
		E3Result e3Result = orderService.createOrder(orderInfo);
		// 删除本地Cooike缓存
		if (e3Result.getStatus() == 200) {
			cartService.deleteCart(user.getId());
		}
		// 返回结果集数据集
		request.setAttribute("orderId", e3Result.getData());
		request.setAttribute("payment", orderInfo.getPayment());
		// 返回支付逻辑视图
		return "success";
	}

	/**
	 * 从Cookie中取购物车数据
	 * 
	 * @param request
	 * @return
	 */
	private List<TbItem> getCookieCartList(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, COOKNAME_CART, true);
		if (StringUtils.isNotBlank(cookieValue)) {
			List<TbItem> itemList = JsonUtils.jsonToList(cookieValue, TbItem.class);
			return itemList;
		}
		return new ArrayList<>();
	}
}
