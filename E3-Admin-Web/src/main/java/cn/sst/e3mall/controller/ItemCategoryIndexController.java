package cn.sst.e3mall.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.search.service.IItemCategory;

@Controller
@RequestMapping("/index")
public class ItemCategoryIndexController {
	
	@Autowired
	private IItemCategory iItemCategory;

	@RequestMapping(value="/item/import")
	@ResponseBody
	public E3Result createItemCategoryIndex() throws IOException{
		return iItemCategory.createItemCategoryIndex();
	}
}
