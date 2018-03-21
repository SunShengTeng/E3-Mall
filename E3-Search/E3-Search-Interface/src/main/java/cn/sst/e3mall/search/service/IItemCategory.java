package cn.sst.e3mall.search.service;

import java.util.List;

import cn.sst.e3mall.search.pojo.ItemCategory;

public interface IItemCategory {

	/**
	 * 获取索引数据
	 * @return
	 */
	public List<ItemCategory> getItemCategory();
	

	/**
	 * 创建索引数据
	 */
	public void createItemCategoryIndex();
}
