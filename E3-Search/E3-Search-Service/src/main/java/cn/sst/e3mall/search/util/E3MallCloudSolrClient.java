package cn.sst.e3mall.search.util;

/**
 * CloudSolrClient的装饰类
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.springframework.stereotype.Service;

@SuppressWarnings("serial")
@Service
public class E3MallCloudSolrClient extends SolrClient {

	/**
	 * CloudSolrClient
	 */

	private CloudSolrClient cloudSolrClient;

	private String DEFAULTCOLLECTION;// 默认的搜索Collection

	private String zkServerOne;

	private String zkServerTwo;

	private String zkServerThree;

	public E3MallCloudSolrClient() {
		super();
	}

	public E3MallCloudSolrClient(String dEFAULTCOLLECTION, String zkServerOne, String zkServerTwo,
			String zkServerThree) {
		super();
		DEFAULTCOLLECTION = dEFAULTCOLLECTION;
		this.zkServerOne = zkServerOne;
		this.zkServerTwo = zkServerTwo;
		this.zkServerThree = zkServerThree;
		final List<String> zkHosts = new ArrayList<String>();
		zkHosts.add(zkServerOne);
		zkHosts.add(zkServerTwo);
		zkHosts.add(zkServerThree);
		System.out.println(DEFAULTCOLLECTION + zkServerOne + zkServerTwo + zkServerThree);
		this.cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkHosts).build();
		final int zkClientTimeout = 10000;
		final int zkConnectTimeout = 10000;
		this.cloudSolrClient.setDefaultCollection(DEFAULTCOLLECTION);
		this.cloudSolrClient.setZkClientTimeout(zkClientTimeout);
		this.cloudSolrClient.setZkConnectTimeout(zkConnectTimeout);

	}

	@Override
	public void close() throws IOException {
		this.cloudSolrClient.close();
	}

	@Override
	public NamedList<Object> request(SolrRequest request, String collection) throws SolrServerException, IOException {

		return this.cloudSolrClient.request(request, collection);
	}

	@Override
	public UpdateResponse add(SolrInputDocument doc) throws SolrServerException, IOException {
		return this.cloudSolrClient.add(doc);
	}

	@Override
	public UpdateResponse commit() throws SolrServerException, IOException {
		return this.cloudSolrClient.commit();
	}
}
