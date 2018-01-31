package cn.sst.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sst.e3mall.commonResult.EasyUIDataGridResult;
import cn.sst.e3mall.service.ItemService;

/**
 * 商品服务
 * @author sunshengteng
 *
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	/**
	 * SOA框架整合测试
	 * @return
	 */
	@RequestMapping("/test")
	@ResponseBody
	public String testItem(){
		return itemService.testItemService();
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult findItemList(Integer page,Integer rows){
		EasyUIDataGridResult itemList = itemService.getItemList(page, rows);
		return itemList;
	}
}
