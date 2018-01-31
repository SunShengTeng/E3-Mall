package cn.sst.e3mall.service;

import cn.sst.e3mall.commonResult.EasyUIDataGridResult;

public interface ItemService {
     
	public String testItemService();
	
	EasyUIDataGridResult getItemList(Integer page,Integer rows);
	
}
