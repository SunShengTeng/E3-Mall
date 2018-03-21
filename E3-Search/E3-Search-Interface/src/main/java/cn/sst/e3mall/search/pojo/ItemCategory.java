package cn.sst.e3mall.search.pojo;

import java.io.Serializable;

import cn.sst.e3mall.pojo.TbItem;

public class ItemCategory extends TbItem implements Serializable{

	private String category_name;

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	
}
