package cn.sst.e3mall.service;


import cn.sst.e3mall.common.Results.EasyUIDataGridResult;
import cn.sst.e3mall.common.Utils.E3Result;
import cn.sst.e3mall.pojo.TbContent;

/**
 * 分类下的内容管理
 * @author sunshengteng
 *
 */
public interface ContentService {

	/**
	 * 根据分类ID，获取分类下的内容
	 * @param categoryId
	 * @return
	 */
	EasyUIDataGridResult findContentListByCategoryId(long categoryId,Integer page,Integer rows);

	/**
	 * 添加分类内容
	 * @param content
	 * @return
	 */
	E3Result insertContent(TbContent content);

	/**
	 * 修改分类下的内容
	 * @param content
	 * @return
	 */
	E3Result updateContentByContent(TbContent content);

	/**
	 * 批量删除分类下的内容
	 * @param split
	 * @return
	 */
	E3Result deleteContentByContentId(String[] split);

}
