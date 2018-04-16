package cn.sst.e3mall.item.pojo;

import cn.sst.e3mall.pojo.TbItem;

@SuppressWarnings("serial")
public class Item extends TbItem {

	public Item() {
		
	}
	public Item(TbItem tbItem) {
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setCreated(tbItem.getCreated());
		this.setId(tbItem.getId());
		this.setImage(tbItem.getImage());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setTitle(tbItem.getTitle());
		this.setUpdated(tbItem.getUpdated());
	}
	
	public String[] getImages() {
		String images = this.getImage();
		if (images != null && !"".equals(images)) {
			String[] strings = images.split(",");
			return strings;
		}
		return null;
	}

	
}
