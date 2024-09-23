package com.example.app.dto;

import java.util.List;

public class OrderRequest {

	 private List<OrderItemRequest> orderItems;
	
	public List<OrderItemRequest> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemRequest> orderItems) {
		this.orderItems = orderItems;
	}
	 

}
