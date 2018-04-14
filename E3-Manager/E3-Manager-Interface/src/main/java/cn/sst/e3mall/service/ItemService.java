package cn.sst.e3mall.service;

import cn.sst.e3mall.common.Results.EasyUIDataGridResult;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.pojo.TbItem;

public interface ItemService {


	/**
	 * 商品列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGridResult getItemList(Integer page, Integer rows);

	/**
	 * 插入商品
	 * 
	 * @param item
	 * @param itemDesc
	 * @return
	 */
	E3Result insertItem(TbItem item, String itemDesc);
	
	/**
	 * 批量删除商品
	 * @param idStrings
	 * @return
	 */
	E3Result deleteItem(String[] idStrings);

}
