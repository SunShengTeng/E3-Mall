package cn.sst.e3mall.search.service;

import java.io.IOException;

import cn.sst.e3mall.common.Utils.E3Result;

public interface IItemCategory {

	/**
	 * 创建索引数据
	 * @throws IOException
	 */
	public E3Result createItemCategoryIndex() throws IOException;
}
