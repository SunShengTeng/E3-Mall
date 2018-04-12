package cn.sst.e3mall.search.util;

/**
 * CloudSolrClient的装饰类
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.StreamingResponseCallback;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
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

	@Override
	public UpdateResponse add(String collection, Collection<SolrInputDocument> docs)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.add(collection, docs);
	}

	@Override
	public UpdateResponse add(Collection<SolrInputDocument> docs) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.add(docs);
	}

	@Override
	public UpdateResponse add(String collection, Collection<SolrInputDocument> docs, int commitWithinMs)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.add(collection, docs, commitWithinMs);
	}

	@Override
	public UpdateResponse add(Collection<SolrInputDocument> docs, int commitWithinMs)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.add(docs, commitWithinMs);
	}

	@Override
	public UpdateResponse add(String collection, SolrInputDocument doc) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.add(collection, doc);
	}

	@Override
	public UpdateResponse add(String collection, SolrInputDocument doc, int commitWithinMs)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.add(collection, doc, commitWithinMs);
	}

	@Override
	public UpdateResponse add(SolrInputDocument doc, int commitWithinMs) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.add(doc, commitWithinMs);
	}

	@Override
	public UpdateResponse add(String collection, Iterator<SolrInputDocument> docIterator)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.add(collection, docIterator);
	}

	@Override
	public UpdateResponse add(Iterator<SolrInputDocument> docIterator) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.add(docIterator);
	}

	@Override
	public UpdateResponse addBean(String collection, Object obj) throws IOException, SolrServerException {
		
		return this.cloudSolrClient.addBean(collection, obj);
	}

	@Override
	public UpdateResponse addBean(Object obj) throws IOException, SolrServerException {
		
		return this.cloudSolrClient.addBean(obj);
	}

	@Override
	public UpdateResponse addBean(String collection, Object obj, int commitWithinMs)
			throws IOException, SolrServerException {
		
		return this.cloudSolrClient.addBean(collection, obj, commitWithinMs);
	}

	@Override
	public UpdateResponse addBean(Object obj, int commitWithinMs) throws IOException, SolrServerException {
		
		return this.cloudSolrClient.addBean(obj, commitWithinMs);
	}

	@Override
	public UpdateResponse addBeans(String collection, Collection<?> beans) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.addBeans(collection, beans);
	}

	@Override
	public UpdateResponse addBeans(Collection<?> beans) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.addBeans(beans);
	}

	@Override
	public UpdateResponse addBeans(String collection, Collection<?> beans, int commitWithinMs)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.addBeans(collection, beans, commitWithinMs);
	}

	@Override
	public UpdateResponse addBeans(Collection<?> beans, int commitWithinMs) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.addBeans(beans, commitWithinMs);
	}

	@Override
	public UpdateResponse addBeans(String collection, Iterator<?> beanIterator)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.addBeans(collection, beanIterator);
	}

	@Override
	public UpdateResponse addBeans(Iterator<?> beanIterator) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.addBeans(beanIterator);
	}

	@Override
	public UpdateResponse commit(String collection) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.commit(collection);
	}

	@Override
	public UpdateResponse commit(String collection, boolean waitFlush, boolean waitSearcher)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.commit(collection, waitFlush, waitSearcher);
	}

	@Override
	public UpdateResponse commit(boolean waitFlush, boolean waitSearcher) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.commit(waitFlush, waitSearcher);
	}

	@Override
	public UpdateResponse commit(String collection, boolean waitFlush, boolean waitSearcher, boolean softCommit)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.commit(collection, waitFlush, waitSearcher, softCommit);
	}

	@Override
	public UpdateResponse commit(boolean waitFlush, boolean waitSearcher, boolean softCommit)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.commit(waitFlush, waitSearcher, softCommit);
	}

	@Override
	public UpdateResponse optimize(String collection) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.optimize(collection);
	}

	@Override
	public UpdateResponse optimize() throws SolrServerException, IOException {
		
		return this.cloudSolrClient.optimize();
	}

	@Override
	public UpdateResponse optimize(String collection, boolean waitFlush, boolean waitSearcher)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.optimize(collection, waitFlush, waitSearcher);
	}

	@Override
	public UpdateResponse optimize(boolean waitFlush, boolean waitSearcher) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.optimize(waitFlush, waitSearcher);
	}

	@Override
	public UpdateResponse optimize(String collection, boolean waitFlush, boolean waitSearcher, int maxSegments)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.optimize(collection, waitFlush, waitSearcher, maxSegments);
	}

	@Override
	public UpdateResponse optimize(boolean waitFlush, boolean waitSearcher, int maxSegments)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.optimize(waitFlush, waitSearcher, maxSegments);
	}

	@Override
	public UpdateResponse rollback(String collection) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.rollback(collection);
	}

	@Override
	public UpdateResponse rollback() throws SolrServerException, IOException {
		
		return this.cloudSolrClient.rollback();
	}

	@Override
	public UpdateResponse deleteById(String collection, String id) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteById(collection, id);
	}

	@Override
	public UpdateResponse deleteById(String id) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteById(id);
	}

	@Override
	public UpdateResponse deleteById(String collection, String id, int commitWithinMs)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteById(collection, id, commitWithinMs);
	}

	@Override
	public UpdateResponse deleteById(String id, int commitWithinMs) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteById(id, commitWithinMs);
	}

	@Override
	public UpdateResponse deleteById(String collection, List<String> ids) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteById(collection, ids);
	}

	@Override
	public UpdateResponse deleteById(List<String> ids) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteById(ids);
	}

	@Override
	public UpdateResponse deleteById(String collection, List<String> ids, int commitWithinMs)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteById(collection, ids, commitWithinMs);
	}

	@Override
	public UpdateResponse deleteById(List<String> ids, int commitWithinMs) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteById(ids, commitWithinMs);
	}

	@Override
	public UpdateResponse deleteByQuery(String collection, String query) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteByQuery(collection, query);
	}

	@Override
	public UpdateResponse deleteByQuery(String query) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteByQuery(query);
	}

	@Override
	public UpdateResponse deleteByQuery(String collection, String query, int commitWithinMs)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteByQuery(collection, query, commitWithinMs);
	}

	@Override
	public UpdateResponse deleteByQuery(String query, int commitWithinMs) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.deleteByQuery(query, commitWithinMs);
	}

	@Override
	public SolrPingResponse ping() throws SolrServerException, IOException {
		
		return this.cloudSolrClient.ping();
	}

	@Override
	public QueryResponse query(String collection, SolrParams params) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.query(collection, params);
	}

	@Override
	public QueryResponse query(SolrParams params) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.query(params);
	}

	@Override
	public QueryResponse query(String collection, SolrParams params, METHOD method)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.query(collection, params, method);
	}

	@Override
	public QueryResponse query(SolrParams params, METHOD method) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.query(params, method);
	}

	@Override
	public QueryResponse queryAndStreamResponse(String collection, SolrParams params,
			StreamingResponseCallback callback) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.queryAndStreamResponse(collection, params, callback);
	}

	@Override
	public QueryResponse queryAndStreamResponse(SolrParams params, StreamingResponseCallback callback)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.queryAndStreamResponse(params, callback);
	}

	@Override
	public SolrDocument getById(String collection, String id) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.getById(collection, id);
	}

	@Override
	public SolrDocument getById(String id) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.getById(id);
	}

	@Override
	public SolrDocument getById(String collection, String id, SolrParams params)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.getById(collection, id, params);
	}

	@Override
	public SolrDocument getById(String id, SolrParams params) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.getById(id, params);
	}

	@Override
	public SolrDocumentList getById(String collection, Collection<String> ids) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.getById(collection, ids);
	}

	@Override
	public SolrDocumentList getById(Collection<String> ids) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.getById(ids);
	}

	@Override
	public SolrDocumentList getById(String collection, Collection<String> ids, SolrParams params)
			throws SolrServerException, IOException {
		
		return this.cloudSolrClient.getById(collection, ids, params);
	}

	@Override
	public SolrDocumentList getById(Collection<String> ids, SolrParams params) throws SolrServerException, IOException {
		
		return this.cloudSolrClient.getById(ids, params);
	}

	@Override
	public DocumentObjectBinder getBinder() {
		
		return this.cloudSolrClient.getBinder();
	}

	@Override
	public int hashCode() {
		
		return this.cloudSolrClient.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		return this.cloudSolrClient.equals(obj);
	}

	

	@Override
	public String toString() {
		
		return this.cloudSolrClient.toString();
	}


}
