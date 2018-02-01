package cn.sst.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sst.e3mall.common.Results.EasyUITreeNode;
import cn.sst.e3mall.service.ItemCatService;

@Controller
@RequestMapping("/item")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 获取商品分类
	 * @param parentId 父分类的ID
	 * @return
	 */
	@RequestMapping("/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> findTreeNodeByParent(@RequestParam(value="id",defaultValue="0")long parentId){
		List<EasyUITreeNode> list = itemCatService.findChildernTreeNode(parentId);
		return list;
	}
	
}
