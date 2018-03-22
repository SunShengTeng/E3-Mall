package cn.sst.e3mall.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.sst.e3mall.common.Utils.E3Result;

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
	 * @throws Exception
	 */
	@Test
	public void testSolrJ() throws Exception {

		SolrClient solrJClient = (SolrClient) getApplicationContext().getBean("httpSolrClient");
		solrJClient.deleteByQuery("*:*");
		solrJClient.commit();

	}
	/**
	 * 测试索引数据导入
	 * @throws IOException
	 */
	public void getItemCategory() throws IOException{
		
	    IItemCategory itemCategory = (IItemCategory) getApplicationContext().getBean("IItemCategoryImpl");
		E3Result e3Result = itemCategory.createItemCategoryIndex();
		System.out.println(e3Result);
		
	}

}
