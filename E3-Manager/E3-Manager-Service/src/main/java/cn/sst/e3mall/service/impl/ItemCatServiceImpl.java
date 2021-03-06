package cn.sst.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sst.e3mall.common.Results.EasyUITreeNode;
import cn.sst.e3mall.mapper.TbItemCatMapper;
import cn.sst.e3mall.pojo.TbItemCat;
import cn.sst.e3mall.pojo.TbItemCatExample;
import cn.sst.e3mall.pojo.TbItemCatExample.Criteria;
import cn.sst.e3mall.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EasyUITreeNode> findChildernTreeNode(Long parentNodeId) {
		// 1、根据parentId查询节点列表
		TbItemCatExample example = new TbItemCatExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentNodeId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		// 2、转换成EasyUITreeNode列表。
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent() ? "closed" : "open");
			// 添加到列表
			resultList.add(node);
		}
		// 3、返回。
		return resultList;
	}

}
