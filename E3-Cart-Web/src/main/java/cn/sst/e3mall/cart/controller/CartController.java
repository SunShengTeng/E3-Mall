package cn.sst.e3mall.cart.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sst.e3mall.cart.service.CartService;
import cn.sst.e3mall.common.Utils.CookieUtils;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.common.Utils.JsonUtils;
import cn.sst.e3mall.pojo.TbItem;
import cn.sst.e3mall.pojo.TbUser;
import cn.sst.e3mall.service.ItemService;

@Controller
public class CartController {

	@Autowired
	private ItemService itemService;

	@Value("${COOKNAME_CART}")
	private String COOKNAME_CART;

	@Autowired
	private CartService cartService;
	
	
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, int num, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TbUser user = (TbUser) request.getAttribute("user");
		// 一、登陆状态（写入reids）
		if (user != null) {
			cartService.addCart(user.getId(), itemId, num);
			//返回逻辑视图
			return "cartSuccess";
		}
		
		// 二、未登陆状态（写入前端缓存）
		// 1、取购物车列表
		List<TbItem> itemList = getCartItemList(request);
		// 2、判断商品是否已经存在购物车中
		Boolean hasItem = false;
		// 2.1购物车中存在此商品
		for (TbItem item : itemList) {
			if (item.getId().longValue() == itemId) {
				item.setNum(item.getNum() + num);
				hasItem = true;
				break;
			}

		}
		// 2.2购物车中不存在此商品
		if (!hasItem) {
			// 取商品
			TbItem tbItem = itemService.getTbItemById(itemId);
			tbItem.setNum(num);
			String images = tbItem.getImage();
			if (images != null) {
				String[] imageList = images.split(",");
				tbItem.setImage(imageList[0]);
			}
			// 添加购物车
			itemList.add(tbItem);
		}
		// 3、写入缓存
		CookieUtils.setCookie(request, response, COOKNAME_CART, JsonUtils.objectToJson(itemList), true);
		return "cartSuccess";
	}

	/**
	 * 购物车列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String cartList(HttpServletRequest request, Model model,HttpServletResponse response) throws Exception {
		List<TbItem> itemList = null;
		// 1、登陆状态
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			// 取cookie购物车列表
			List<TbItem> cookieCartList = getCartItemList(request);
			// 合并服务端跟本地的购物车
			itemList = cartService.mergeCartList(cookieCartList, user.getId());
			// 删除客户端的缓存
			CookieUtils.deleteCookie(request, response, COOKNAME_CART);
			// 返回
			model.addAttribute("cartList", itemList);
			return "cart";
		}
		// 2、未登陆状态
		itemList = getCartItemList(request);
		model.addAttribute("cartList", itemList);
		return "cart";
	}

	/**
	 * 更新购物车内商品的购买数量
	 * 
	 * @param request
	 * @param response
	 * @param itemId
	 * @param num
	 *            购买的商品总数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result increItemNumber(HttpServletRequest request, HttpServletResponse response, @PathVariable Long itemId,
			@PathVariable int num) throws Exception {
		TbUser user = (TbUser) request.getAttribute("user");
		// 1、登陆状态
		if (user != null) {
			// 更新服务端的购物车列表
			cartService.updateCartItemNumber(user.getId(), itemId,num);
		}
		// 2、非登陆状态
		// 修改购物车商品数量
		List<TbItem> itemList = getCartItemList(request);
		for (TbItem tbItem : itemList) {
			if (tbItem.getId().longValue() == itemId) {
				tbItem.setNum(num);
			}
		}
		// 更新Cookie内容
		CookieUtils.setCookie(request, response, COOKNAME_CART, JsonUtils.objectToJson(itemList), true);
		return E3Result.ok();
	}
	/**
	 * 从购物车中删除商品
	 * @param itemId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteItemAtCartByItemId(@PathVariable Long itemId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 1、登陆状态
		TbUser user = (TbUser) request.getAttribute("user");
		if (user != null) {
			cartService.deleteItemFromCart(user.getId(), itemId);
		}
		// 2、未登陆状态
		// 获取购物车列表
		List<TbItem> itemList = getCartItemList(request);
		// 从购物车删除商品
		for (TbItem tbItem : itemList) {
			if (tbItem.getId().longValue() == itemId) {
				itemList.remove(tbItem);
				break;
			}
		}
		// 向缓存中写入删除后的商品列表
		CookieUtils.setCookie(request, response, COOKNAME_CART, JsonUtils.objectToJson(itemList), true);
		return "redirect:/cart/cart";
	}

	/**
	 * 从Cookie中取购物车列表
	 * 
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartItemList(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, COOKNAME_CART, true);
		if (StringUtils.isNotBlank(cookieValue)) {
			return JsonUtils.jsonToList(cookieValue, TbItem.class);
		}
		return new ArrayList<>();
	}
}
