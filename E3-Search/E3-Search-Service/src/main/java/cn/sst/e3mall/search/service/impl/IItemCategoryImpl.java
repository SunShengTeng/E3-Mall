package cn.sst.e3mall.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.common.pojo.ItemCategory;
import cn.sst.e3mall.common.pojo.SearchResult;
import cn.sst.e3mall.mapper.TbItemCategoryMapper;
import cn.sst.e3mall.search.dao.ItemCategoryDao;
import cn.sst.e3mall.search.service.IItemCategory;

@Service
public class IItemCategoryImpl implements IItemCategory {

	@Autowired
	private ItemCategoryDao itemCategoryDao; // 索引库Dao
	@Autowired
	private SolrClient httpSolrClient;

	@Value("${DEFAULT_FIELD}")
	private String DEFAULT_FIELD; // 默认搜索域
	@Autowired
	private TbItemCategoryMapper tbItemCategoryMapper; // 

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

	@Override
	public SearchResult searchItemList(String keywords, int page, int rows) throws Exception {
		// 封装SolrQuery
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(keywords);
		// 设置分页条件
		query.setStart((page - 1) * rows);
		// 设置rows
		query.setRows(rows);
		// 设置默认搜索域
		query.set("df", DEFAULT_FIELD);
		// 设置高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		// 返回最终结果
		SearchResult searchResult = itemCategoryDao.searchItemIndex(query);
		// 计算总页数
		int recourdCount = searchResult.getTotalCount();
		int pages = recourdCount / rows;
		if (recourdCount % rows > 0) pages++;
		searchResult.setTotalPages(pages);
		
		// 全局异常类调试
		// int i = 1/0;
		
		return searchResult;
	}

}
