package cn.sst.e3mall.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sst.e3mall.common.Results.EasyUIDataGridResult;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.pojo.TbContent;
import cn.sst.e3mall.pojo.TbOrder;
import cn.sst.e3mall.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {

	
	@Autowired
	private ContentService contentServiceImpl;
	
	/**
	 * 查询分类下的内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGridResult findContentListByCategoryId(long categoryId,Integer page,Integer rows){
		EasyUIDataGridResult results = contentServiceImpl.findContentListByCategoryId(categoryId,page,rows);
		return results;
	}
	
	/**
	 * 添加分类下的内容
	 * @param content
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public E3Result createContent(TbContent content){
		return contentServiceImpl.insertContent(content);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public E3Result updateContentByContent(TbContent content){
		return contentServiceImpl.updateContentByContent(content);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public E3Result deleteContentByContentId(String ids){
		String[] split = ids.split(",");
		return contentServiceImpl.deleteContentByContentId(split);
	}
}
