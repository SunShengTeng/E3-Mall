package cn.sst.e3mall.search.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.sst.e3mall.common.pojo.ItemCategory;
import cn.sst.e3mall.mapper.TbItemCategoryMapper;
import cn.sst.e3mall.search.dao.ItemCategoryDao;
import cn.sst.e3mall.search.service.IItemCategory;

/**
 * 
 * @author sunshengteng
 *
 */
public class SolrJTest {

	/**
	 * 加载配置文件
	 */
	public ApplicationContext getApplicationContext() {
		return new ClassPathXmlApplicationContext("classpath*:Spring/ApplicationContext-*.xml");
	}

	/**
	 * 删除索引库数据
	 * 
	 * @throws Exception
	 */

	public void deleteIndex() throws Exception {

		SolrClient solrJClient = (SolrClient) getApplicationContext().getBean("httpSolrClient");
		solrJClient.deleteByQuery("*:*");
		solrJClient.commit();

	}

	/**
	 * 测试索引数据导入-单机版
	 * 
	 * @throws IOException
	 */

	@Test
	public void createIndex() throws IOException {

		IItemCategory itemCategory = (IItemCategory) getApplicationContext().getBean("IItemCategoryImpl");
		itemCategory.createItemCategoryIndex();

	}

	/**
	 * 测试查询
	 * 
	 * @throws Exception
	 */

	public void queryIndexByConditions() throws Exception {

		IItemCategory itemCategory = (IItemCategory) getApplicationContext().getBean("IItemCategoryImpl");
		List<ItemCategory> itemlist = itemCategory.searchItemList("三星", 1, 10).getItemlist();
		for (ItemCategory item : itemlist) {
			System.out.println(item.getTitle());
		}

	}

	/**
	 * 集群版Solrj创建索引（没有Spring版）
	 * 
	 * @throws IOException
	 */
	@Test
	public void createIndexCloud() throws IOException {
		final List<String> zkHosts = new ArrayList<String>();
		zkHosts.add("192.168.169.162:2181");
		zkHosts.add("192.168.169.162:2182");
		zkHosts.add("192.168.169.162:2183");
		CloudSolrClient httpSolrClient = new CloudSolrClient.Builder().withZkHost(zkHosts).build();
		final int zkClientTimeout = 10000;
		final int zkConnectTimeout = 10000;
		httpSolrClient.setDefaultCollection("e3mall");
		httpSolrClient.setZkClientTimeout(zkClientTimeout);
		httpSolrClient.setZkConnectTimeout(zkConnectTimeout);
		// 获取数据
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "1919871973197");
		document.addField("item_title", "飞机杯");
		document.addField("item_sell_point", "进口飞机杯");

		try {
			httpSolrClient.add(document);
			httpSolrClient.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建导入索引数据（Spring版）
	 * @throws Exception 
	 * @throws SolrServerException 
	 */
	@Test
	public void createIndexCloudBySpring() throws SolrServerException, Exception {

		SolrClient solrClient = (SolrClient) getApplicationContext().getBean("httpSolrClient");
		SolrInputDocument document = new SolrInputDocument();

		document.addField("id", "19198719731937");
		document.addField("item_title", "飞机杯3");
		document.addField("item_sell_point", "进口飞机杯");
		solrClient.add(document);
		solrClient.commit();
	}

}
