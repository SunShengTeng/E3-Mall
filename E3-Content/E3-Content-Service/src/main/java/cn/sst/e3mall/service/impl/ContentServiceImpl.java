package cn.sst.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.sst.e3mall.common.Jedis.JedisClient;
import cn.sst.e3mall.common.Results.EasyUIDataGridResult;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.common.Utils.JsonUtils;
import cn.sst.e3mall.mapper.TbContentMapper;
import cn.sst.e3mall.pojo.TbContent;
import cn.sst.e3mall.pojo.TbContentExample;
import cn.sst.e3mall.pojo.TbContentExample.Criteria;
import cn.sst.e3mall.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${E3MALL_CATEGORY}")
	private String CATEGORY;

	@Override
	public EasyUIDataGridResult findContentListByCategoryId(long categoryId, Integer page, Integer rows) {

		EasyUIDataGridResult result = new EasyUIDataGridResult();

		// 1、先查询Redis缓存
		try {
			String json = jedisClient.hget(CATEGORY, categoryId + "");
			if (StringUtils.isNotBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				result.setRows(list);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 2、缓存没有的话查询数据库
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);

		List<TbContent> contentList = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(contentList);

		result.setTotal(pageInfo.getTotal());
		result.setRows(contentList);
		// 3、向缓存中添加数据
		try {
			jedisClient.hset(CATEGORY, categoryId + "", JsonUtils.objectToJson(contentList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public E3Result insertContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insertSelective(content);
		// 同步Redis缓存
		jedisClient.hdel(CATEGORY, content.getCategoryId().toString());
		return E3Result.ok();
	}

	@Override
	public E3Result updateContentByContent(TbContent content) {
		contentMapper.updateByPrimaryKeySelective(content);
		// 同步Redis缓存
		jedisClient.hdel(CATEGORY, content.getCategoryId().toString());
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContentByContentId(String[] split) {
		TbContent tbContent = contentMapper.selectByPrimaryKey(Long.parseLong(split[0]));
		for (String string : split) {
			contentMapper.deleteByPrimaryKey(Long.parseLong(string));
		}

		//同步缓存数据
		jedisClient.hdel(CATEGORY, tbContent.getCategoryId().toString());
		return E3Result.ok();
	}

	@Override
	public List<TbContent> getContentByCategoryId(Long cid) {
		TbContentExample contentExample = new TbContentExample();
		Criteria criteria = contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExample(contentExample);
		return list;
	}
}
