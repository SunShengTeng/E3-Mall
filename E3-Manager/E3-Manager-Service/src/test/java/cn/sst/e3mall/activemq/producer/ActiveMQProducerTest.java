package cn.sst.e3mall.activemq.producer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActiveMQProducerTest {

	/**
	 * 发送消息
	 */
	@Test
	public void sendMessage() {
		@SuppressWarnings("resource")
		// 第一步：初始化一个spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:Spring/ApplicationContext-ActiveMQ.xml");
		// 第二步：从容器中获得JMSTemplate对象。
		JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
		// 第三步：从容器中获得一个Destination对象
		Destination destination = (Destination) applicationContext.getBean("queueDestination");
		// 第四步：使用JMSTemplate对象发送消息，需要知道Destination
		jmsTemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage("发送消息");
				return message;
			}
		});
	}
	

}
