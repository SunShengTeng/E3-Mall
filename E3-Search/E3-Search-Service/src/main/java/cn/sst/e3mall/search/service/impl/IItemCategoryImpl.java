package cn.sst.e3mall.search.service.impl;


import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.mapper.TbItemCategoryMapper;
import cn.sst.e3mall.search.pojo.ItemCategory;
import cn.sst.e3mall.search.service.IItemCategory;
@Service
public class IItemCategoryImpl implements IItemCategory {


	@Autowired
	private SolrClient httpSolrClient;
	
	@Autowired
	private TbItemCategoryMapper tbItemCategoryMapper;
	
	@Override
	public E3Result createItemCategoryIndex() throws IOException {

		// 获取数据
		List<ItemCategory> itemCategory = tbItemCategoryMapper.itemLeftJoinItemCategory();
		// 遍历-想索引库添加数据
		try {
			for (ItemCategory item : itemCategory) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", item.getId());
				document.addField("item_title", item.getTitle());
				document.addField("item_sell_point", item.getSellPoint());
				document.addField("item_price", item.getPrice());
				document.addField("item_image", item.getImage());
				document.addField("item_category_name", item.getCategory_name());
				httpSolrClient.add(document);
			}
			// 提交
			httpSolrClient.commit();
			
			// 响应前端
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(200, "索引库写入失败");
		} finally {
			httpSolrClient.close();
		}
		
		
	}

}
