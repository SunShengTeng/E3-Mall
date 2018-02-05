package cn.sst.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.sst.e3mall.common.Results.EasyUIDataGridResult;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.mapper.TbContentMapper;
import cn.sst.e3mall.pojo.TbContent;
import cn.sst.e3mall.pojo.TbContentExample;
import cn.sst.e3mall.pojo.TbContentExample.Criteria;
import cn.sst.e3mall.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public EasyUIDataGridResult findContentListByCategoryId(long categoryId, Integer page, Integer rows) {

		EasyUIDataGridResult result = new EasyUIDataGridResult();

		PageHelper.startPage(page, rows);

		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);

		List<TbContent> contentList = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(contentList);

		result.setTotal(pageInfo.getTotal());
		result.setRows(contentList);
		return result;
	}

	@Override
	public E3Result insertContent(TbContent content){
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insertSelective(content);
		return E3Result.ok();
	}

	@Override
	public E3Result updateContentByContent(TbContent content) {
	    contentMapper.updateByPrimaryKeySelective(content);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContentByContentId(String[] split) {
        for (String string : split) {
			contentMapper.deleteByPrimaryKey(Long.parseLong(string));
		}
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
