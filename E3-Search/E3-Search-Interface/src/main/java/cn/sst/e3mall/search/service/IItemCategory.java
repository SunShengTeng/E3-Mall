package cn.sst.e3mall.search.service;

import java.io.IOException;

import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.common.pojo.SearchResult;
/**
 * 索引数据操作类
 * @author sunshengteng
 *
 */
public interface IItemCategory {

	/**
	 * 创建索引数据
	 * @throws IOException
	 */
	public E3Result createItemCategoryIndex() throws IOException;
	/**
	 * 索引商品数据
	 * @param keywords
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception 
	 */
	public SearchResult searchItemList(String keywords, int page, int rows) throws Exception;
	/**
	 * 根据商品消息ID添加商品索引
	 * @param itemId
	 * @return
	 */
	public E3Result insertIndexCategoryIndex(Long itemId)throws Exception;
	
}
