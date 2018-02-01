package cn.sst.e3mall.service;

import java.util.List;

import cn.sst.e3mall.common.Results.EasyUITreeNode;

public interface ItemCatService {
  
 
	List<EasyUITreeNode> findChildernTreeNode(Long parentNodeId);
}
