package com.example.app.service;

import java.util.List;

import com.example.app.dto.OrderRequest;
import com.example.app.dto.OrderResponseDto;
import com.example.app.dto.OrdersResponseDto;
import com.example.app.entity.OrderEntity;

public interface OrderService {
	
	public String createOrder(OrderRequest orderRequest,int userId);
	
	public List<OrdersResponseDto> getOrders(int userId);
	
	public OrderResponseDto getOrder(int order_id);
	
	
	

}
