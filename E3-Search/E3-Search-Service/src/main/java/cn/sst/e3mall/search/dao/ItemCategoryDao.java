package cn.sst.e3mall.search.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
/**
 * 索引库Dao
 * @author sunshengteng
 */

import cn.sst.e3mall.common.pojo.ItemCategory;
import cn.sst.e3mall.common.pojo.SearchResult;
@Repository
public class ItemCategoryDao {

	@Autowired
	private SolrClient httpSolrClient;
	
	public SearchResult searchItemIndex(SolrQuery query) throws Exception{
		// 根据条件查询索引库
		QueryResponse response = httpSolrClient.query(query);
		// 获取结果集（包含：普通、高亮）
		SolrDocumentList documentList = response.getResults();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		// 总记录数
		// 封装响应对象
		SearchResult result = new SearchResult();
		ArrayList<ItemCategory> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : documentList) {
			ItemCategory itemCategory = new ItemCategory();
			//取商品信息
			itemCategory.setCategory_name((String) solrDocument.get("item_category_name"));
			itemCategory.setId((String)solrDocument.get("id"));;
			itemCategory.setImage((String) solrDocument.get("item_image"));
			itemCategory.setPrice((float) solrDocument.get("item_price"));
			itemCategory.setSellPoint((String) solrDocument.get("item_sell_point"));
			//取高亮结果
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = "";
			if (list != null && list.size() > 0) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			itemCategory.setTitle(itemTitle);
			//添加到商品列表
			itemList.add(itemCategory);

		}
		result.setItemlist(itemList);
		result.setTotalCount((int)documentList.getNumFound());
		return result;
	};
}
