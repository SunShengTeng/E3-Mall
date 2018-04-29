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

import cn.sst.e3mall.common.Utils.CookieUtils;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.common.Utils.JsonUtils;
import cn.sst.e3mall.pojo.TbItem;
import cn.sst.e3mall.service.ItemService;

@Controller
public class CartController {

	@Autowired
	private ItemService itemService;

	@Value("${COOKNAME_CART}")
	private String COOKNAME_CART;

	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, int num, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		// 未登陆状态（写入前端缓存）
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
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String cartList(HttpServletRequest request,Model model) throws Exception{
		// 1、登陆状态
		// 2、未登陆状态
		List<TbItem> itemList = getCartItemList(request);
		model.addAttribute("cartList", itemList);
		return "cart";
	}
	/**
	 * 更新购物车内商品的购买数量
	 * @param request
	 * @param response
	 * @param itemId
	 * @param num 购买的商品总数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result increItemNumber(HttpServletRequest request, HttpServletResponse response,@PathVariable Long itemId,@PathVariable int num) throws Exception{
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
