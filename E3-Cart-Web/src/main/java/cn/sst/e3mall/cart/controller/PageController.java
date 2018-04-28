package cn.sst.e3mall.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/cart/cart")
	public String cartPageController(){
		return "cart";
	}
	
	@RequestMapping("/error")
	public String errorPageController(){
		return "error";
	}
}
