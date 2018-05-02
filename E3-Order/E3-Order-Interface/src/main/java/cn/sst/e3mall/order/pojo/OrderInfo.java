package cn.sst.e3mall.order.pojo;

import java.io.Serializable;
import java.util.List;

import cn.sst.e3mall.pojo.TbItem;
import cn.sst.e3mall.pojo.TbOrder;
import cn.sst.e3mall.pojo.TbOrderItem;
import cn.sst.e3mall.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder implements Serializable{

	private List<TbOrderItem> orderItems;
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
	
}
