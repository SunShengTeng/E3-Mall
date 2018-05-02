package cn.sst.e3mall.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.sst.e3mall.item.pojo.Item;
import cn.sst.e3mall.pojo.TbItem;
import cn.sst.e3mall.pojo.TbItemDesc;
import cn.sst.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private FreeMarkerConfigurer freemarker;
	
	@RequestMapping("/{itemId}")
	public String getItemAndItemDescById(@PathVariable long itemId,Model model) throws Exception{
		TbItem tbItem = itemService.getTbItemById(itemId);
		Item item = new Item(tbItem);
		TbItemDesc itemDesc = itemService.getTbItemDescById(itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
	/**
	 * 生成商品静态页面
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public void generateStaticHtmlByItemId(long itemId) throws Exception{
		// 1、获取模版对象
		Configuration configuration = freemarker.getConfiguration();
		Template template = configuration.getTemplate("item.ftl");
		// 2、构建商品数据集
		HashMap<Object,Object> map = new HashMap<>();
		TbItem tbItem = itemService.getTbItemById(itemId);
		Item item = new Item(tbItem);
		TbItemDesc itemDesc = itemService.getTbItemDescById(itemId);
		map.put("item", item);
		map.put("itemDesc", itemDesc);
		// 3、指定输出目录
		Writer output = new FileWriter(new File("/Users/sunshengteng/Java_project/Workspaces/Eclipse/Products/E3-Mall/E3-Item-Web/src/main/webapp/WEB-INF/HTML/item/"+itemId+".html"));
		// 4、输出文件
		template.process(map, output);
		// 5、关流
		output.close();
		
	}
}
