package cn.sst.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.sst.e3mall.service.ContentService;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentServiceImpl;
	
	

}
