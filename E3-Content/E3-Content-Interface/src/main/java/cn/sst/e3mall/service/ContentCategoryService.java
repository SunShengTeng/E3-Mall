package cn.sst.e3mall.service;

import java.util.List;

import cn.sst.e3mall.common.Results.EasyUITreeNode;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.pojo.TbContent;

public interface ContentCategoryService {

	/**
	 * 根据父分类ID查询所有的子分类
	 * @param parentId
	 * @return
	 */
	List<EasyUITreeNode> findContentCatagoryNodeByParentNodeId(Long parentId);

	/**
	 * 插入ContentCategory节点
	 * @param parentId
	 * @param name
	 * @return
	 */
	E3Result insertContentCategory(long parentId, String name);

	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	E3Result deleteContentCategory(long id);

	/**
	 * 更新分类名称
	 * @param id
	 * @param name
	 * @return
	 */
	E3Result updateContentCategory(Long id, String name);
	
}
