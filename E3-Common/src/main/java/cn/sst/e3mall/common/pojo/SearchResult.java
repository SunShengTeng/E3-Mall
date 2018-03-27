package cn.sst.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SearchResult implements Serializable{

	private List<ItemCategory> itemlist;// 商品信息（包含分类名称）

	private int totalCount; // 总记录数
	
	private int totalPages; //总页数
	
	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<ItemCategory> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<ItemCategory> itemlist) {
		this.itemlist = itemlist;
	}
	
}
