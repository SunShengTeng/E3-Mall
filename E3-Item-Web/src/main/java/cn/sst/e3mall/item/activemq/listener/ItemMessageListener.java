package cn.sst.e3mall.item.activemq.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.sst.e3mall.item.controller.ItemController;
import cn.sst.e3mall.service.ItemService;
/**
 * 生成静态页面的Listener
 * @author sunshengteng
 *
 */
public class ItemMessageListener implements MessageListener {

	@Autowired
	private ItemController itemController;
	
	
	@Override
	public void onMessage(Message message) {

		try {
			TextMessage textMessage = null;
			Long itemId = null;
			// 取商品id
			if (message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				itemId = Long.parseLong(textMessage.getText());
			}
			// 等待添加商品的事务提交，避免根据商品ID查不到商品
			Thread.sleep(1000);
			// 向索引库添加文档
			itemController.generateStaticHtmlByItemId(itemId);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
