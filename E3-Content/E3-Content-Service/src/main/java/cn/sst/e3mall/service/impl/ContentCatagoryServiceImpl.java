package cn.sst.e3mall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sst.e3mall.common.Results.EasyUITreeNode;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.mapper.TbContentCategoryMapper;
import cn.sst.e3mall.mapper.TbContentMapper;
import cn.sst.e3mall.pojo.TbContent;
import cn.sst.e3mall.pojo.TbContentCategory;
import cn.sst.e3mall.pojo.TbContentCategoryExample;
import cn.sst.e3mall.pojo.TbContentCategoryExample.Criteria;
import cn.sst.e3mall.pojo.TbContentExample;
import cn.sst.e3mall.service.ContentCategoryService;

@Service
public class ContentCatagoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EasyUITreeNode> findContentCatagoryNodeByParentNodeId(Long parentId) {
		List<EasyUITreeNode> result = new ArrayList<>();
		TbContentCategoryExample categoryExample = new TbContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(categoryExample);
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(tbContentCategory.getId());
			easyUITreeNode.setText(tbContentCategory.getName());
			easyUITreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			result.add(easyUITreeNode);
		}

		return result;
	}

	@Override
	public E3Result insertContentCategory(long parentId, String name) {
		// 1、接收两个参数：parentId、name
		// 2、向tb_content_category表中插入数据。
		// a)创建一个TbContentCategory对象
		TbContentCategory tbContentCategory = new TbContentCategory();
		// b)补全TbContentCategory对象的属性
		tbContentCategory.setIsParent(false);
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentId);
		// 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		tbContentCategory.setSortOrder(1);
		// 状态。可选值:1(正常),2(删除)
		tbContentCategory.setStatus(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		// c)向tb_content_category表中插入数据
		contentCategoryMapper.insert(tbContentCategory);
		// 3、判断父节点的isparent是否为true，不是true需要改为true。
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			// 更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		// 4、需要主键返回。
		// 5、返回E3Result，其中包装TbContentCategory对象
		return E3Result.ok(tbContentCategory);
	}

	@Override
	public E3Result deleteContentCategory(long id) {

		// 1、查处要删除的节点
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		// 2、查询改节点的兄弟节点
		TbContentCategoryExample contentCategoryExample = new TbContentCategoryExample();
		Criteria criteria = contentCategoryExample.createCriteria();
		criteria.andParentIdEqualTo(contentCategory.getParentId());
		int countByExample = contentCategoryMapper.countByExample(contentCategoryExample);
		if (countByExample > 1) {
			// 3、有兄弟，直接删除当前子节点
			contentCategoryMapper.deleteByPrimaryKey(id);
		} else {
			// 4.1、没有兄弟节点了，删除改节点以后要更改父节点的isParent
			TbContentCategory parentContentCategory = new TbContentCategory();
			parentContentCategory.setId(contentCategory.getParentId());
			parentContentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKeySelective(parentContentCategory);

			contentCategoryMapper.deleteByPrimaryKey(id);
		}
		// TODO :需要加一个判断，如果删除的是父分类，需要给出解决方案：1、级联删除，2、给友情提示“只能一级一级的删除”
		// 4、响应结果
		return E3Result.ok();
	}

	@Override
	public E3Result updateContentCategory(Long id, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setId(id);
		contentCategory.setName(name);
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return E3Result.ok();
	}

}
