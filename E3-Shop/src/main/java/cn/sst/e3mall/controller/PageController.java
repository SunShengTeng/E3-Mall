package cn.sst.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import cn.sst.e3mall.pojo.TbContent;
import cn.sst.e3mall.service.ContentService;

/**
 * 页面控制
 * @author sunshengteng
 *
 */
@Controller
public class PageController {
	
	@Value("${CONTENT_LUNBO_ID}")
	private Long CONTENT_LUNBO_ID;
	
	@Autowired
	private ContentService contentServiceImpl;

	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String showIndex(Model module){
		// 查找首页轮播图数据
		List<TbContent> contentList = contentServiceImpl.getContentByCategoryId(CONTENT_LUNBO_ID);
		module.addAttribute("ad1List", contentList);
		return "index";
	}
	
	/**
	 * jsp页面Controller
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
	
}
