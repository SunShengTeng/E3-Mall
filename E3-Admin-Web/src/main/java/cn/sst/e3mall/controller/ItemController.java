package cn.sst.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sst.e3mall.common.Results.EasyUIDataGridResult;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.pojo.TbItem;
import cn.sst.e3mall.service.ItemService;

/**
 * 商品服务
 * 
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
	 * 
	 * @return
	 */
	@RequestMapping("/test")
	@ResponseBody
	public String testItem() {
		return itemService.testItemService();
	}

	/**
	 * 查询你商品列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult findItemList(Integer page, Integer rows) {
		EasyUIDataGridResult itemList = itemService.getItemList(page, rows);
		return itemList;
	}

	/**
	 * 商品添加功能
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem item, String desc) {
		E3Result result = itemService.insertItem(item, desc);
		return result;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public E3Result deleteItemByIds(String ids) {
		E3Result result = null;
		String[] split = ids.split(",");
		try {
			result = itemService.deleteItem(split);
		} catch (Exception e) {
			e.printStackTrace();
			result = E3Result.build(201, "删除失败");
		}
		return result;
	}

}
