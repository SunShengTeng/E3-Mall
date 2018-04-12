package cn.sst.e3mall.search.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.sst.e3mall.common.pojo.SearchResult;
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
	
	@Value("${PAGE_ROWS}")
	private Integer PAGE_ROWS;
	
	@RequestMapping(value="/search")
	public String searchItemList(String keyword,@RequestParam(defaultValue = "1") Integer page,Model model) throws Exception{
		// 转换get请求中的中文乱码
		keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		// 查询索引数据
		SearchResult result = itemService.searchItemList(keyword, page, PAGE_ROWS);
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("recourdCount", result.getTotalCount());
		model.addAttribute("page", page);
		model.addAttribute("itemList", result.getItemlist());
		
		// 返回逻辑视图
		return "search";
	}
}
