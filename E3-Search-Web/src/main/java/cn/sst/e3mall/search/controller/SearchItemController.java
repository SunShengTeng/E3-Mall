package cn.sst.e3mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.sst.e3mall.search.service.IItemCategory;

/**
 * 商品搜索
 * @author sunshengteng
 *
 */
@Controller
public class SearchItemController {

	@Autowired
	private IItemCategory itemService;
	
	@RequestMapping(value="/search")
	public String searchItemList(String keyword){
		
		
		
		return "search";
	}
}
