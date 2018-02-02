package cn.sst.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sst.e3mall.common.Results.EasyUITreeNode;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryServiceImpl;
	
	/**
	 * 根据父分类ID查询所有的子分类
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> findContentCatagoryNodeByParentNodeId(@RequestParam(value="id",defaultValue="0")Long parentId){
		return contentCategoryServiceImpl.findContentCatagoryNodeByParentNodeId(parentId);
	}
	
	/**
	 * 插入ContentCategory节点
	 * @param parentId
	 * @param name
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public E3Result createContentCategory(long parentId,String name){
		return contentCategoryServiceImpl.insertContentCategory(parentId,name);
	}
	
	/**
	 * 删除分类
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public E3Result deleteContentCategory(long id){
		return contentCategoryServiceImpl.deleteContentCategory(id);
	}
	
	/**
	 * 更新分类名称
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping("/update")
    @ResponseBody
    public E3Result updateContentCategory(Long id,String name){
		return contentCategoryServiceImpl.updateContentCategory(id,name);
	}
}
