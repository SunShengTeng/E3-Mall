package cn.sst.e3mall.search.test;

import org.apache.solr.client.solrj.SolrClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author sunshengteng
 *
 */
public class SolrJTest {
	private SolrClient getHttpSolrJClient() {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:Spring/ApplicationContext-*.xml");
		return (SolrClient) applicationContext.getBean("httpSolrClient");
	}

	@Test
	public void testSolrJ() throws Exception {

		SolrClient solrClient = getHttpSolrJClient();
		solrClient.deleteByQuery("*:*");
		solrClient.commit();

	}

}
