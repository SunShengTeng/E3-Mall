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
}