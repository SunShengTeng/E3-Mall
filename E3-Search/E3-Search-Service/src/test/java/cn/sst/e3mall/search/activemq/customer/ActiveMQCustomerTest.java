package cn.sst.e3mall.search.activemq.customer;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActiveMQCustomerTest {
	/**
	 * 接受消息
	 * @throws Exception 
	 */
	@Test
	public void recevieMessage() throws Exception{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:Spring/ApplicationContext-ActiveMQ.xml");
		System.in.read();
	}
}
