package cn.sst.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.sst.e3mall.common.Jedis.JedisClient;
import cn.sst.e3mall.common.Results.EasyUIDataGridResult;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.common.Utils.IDUtils;
import cn.sst.e3mall.common.Utils.JsonUtils;
import cn.sst.e3mall.mapper.TbItemDescMapper;
import cn.sst.e3mall.mapper.TbItemMapper;
import cn.sst.e3mall.pojo.Item;
import cn.sst.e3mall.pojo.TbItem;
import cn.sst.e3mall.pojo.TbItemDesc;
import cn.sst.e3mall.pojo.TbItemExample;
import cn.sst.e3mall.pojo.TbItemExample.Criteria;
import cn.sst.e3mall.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Resource
	private Destination itemTopicDestination;// 商品添加话题
	
	// ActiveMQ
	@Autowired
	private JmsTemplate jmsTemplate;
	// Redis
	@Autowired
	private JedisClient jedisClientCluster;

	
	@Value("${ITEM_INFO_PREF}")
	private String ITEM_INFO_PREF;
	@Value("${CACHE_EXPIRE}")
	private Integer CACHE_EXPIRE;

	public ItemServiceImpl() {
		super();
	}

	/**
	 * 商品列表服务
	 */
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		// 取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);

		// 创建返回结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);

		return result;
	}

	/*
	 * 添加商品
	 * 
	 * @see
	 * cn.sst.e3mall.service.ItemService#insertItem(cn.sst.e3mall.pojo.TbItem,
	 * java.lang.String)
	 */
	@Override
	public E3Result insertItem(TbItem item, String itemDesc) {
		// 1、生成商品id
		long itemId = IDUtils.genItemId();
		// 2、补全TbItem对象的属性
		item.setId(itemId);
		// 商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 3、向商品表插入数据
		itemMapper.insert(item);
		// 4、创建一个TbItemDesc对象
		TbItemDesc itemDescO = new TbItemDesc();
		// 5、补全TbItemDesc的属性
		itemDescO.setItemId(itemId);
		itemDescO.setItemDesc(itemDesc);
		itemDescO.setCreated(date);
		itemDescO.setUpdated(date);
		// 6、向商品描述表插入数据
		itemDescMapper.insert(itemDescO);
		// 7、发送商品添加消息
		jmsTemplate.send(itemTopicDestination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				// 发送商品ID
				return session.createTextMessage(itemId + "");
			}
		});
		// 8、E3Result.ok()
		return E3Result.ok();
	}

	/**
	 * 删除商品
	 */
	@Override
	public E3Result deleteItem(String[] idStrings) {
		for (String itemId : idStrings) {
			itemMapper.deleteByPrimaryKey(Long.parseLong(itemId));
		}
		return E3Result.ok();
	}

	@Override
	public TbItem getTbItemById(Long itemId) {
		// 查询缓存
		try {
			String itemJson = jedisClientCluster.get(ITEM_INFO_PREF + ":" + itemId + ":BASE");
			if (StringUtils.isNotBlank(itemJson)) {
				return JsonUtils.jsonToPojo(itemJson, TbItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemExample tbItemExample = new TbItemExample();
		Criteria criteria = tbItemExample.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> items = itemMapper.selectByExample(tbItemExample);
		if (items != null && items.size() > 0) {
			TbItem item = items.get(0);
			// 添加缓存，并设置过期时间
			try {
				jedisClientCluster.set(ITEM_INFO_PREF + ":" + item.getId() + ":BASE", JsonUtils.objectToJson(item));
				jedisClientCluster.expire(ITEM_INFO_PREF + ":" + item.getId() + ":BASE", CACHE_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return item;
		}
		return null;
	}

	@Override
	public TbItemDesc getTbItemDescById(long itemId) {
		// 查询缓存
		try {
			String itemJson = jedisClientCluster.get(ITEM_INFO_PREF + ":" + itemId + ":DESC");
			if (StringUtils.isNotBlank(itemJson)) {
				return JsonUtils.jsonToPojo(itemJson, TbItemDesc.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		// 添加缓存，并设置过期时间
		try {
			jedisClientCluster.set(ITEM_INFO_PREF + ":" + itemDesc.getItemId() + ":DESC",
					JsonUtils.objectToJson(itemDesc));
			jedisClientCluster.expire(ITEM_INFO_PREF + ":" + itemDesc.getItemId() + ":DESC", CACHE_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;

	}

}
