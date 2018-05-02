package cn.sst.e3mall.mapper;

import java.util.List;

import cn.sst.e3mall.common.pojo.ItemCategory;


/**
 * 查询商品信息，构建索引库
 * @author sunshengteng
 *
 */
public interface TbItemCategoryMapper {

	/**
	 * 查询商品并关联分类信息
	 * @return
	 */
	public List<ItemCategory> itemLeftJoinItemCategory();
	/**
	 * 根据商品ID查询商品索引需要的数据
	 * @param itemId
	 * @return
	 */
	public ItemCategory getItemById(Long itemId);
}