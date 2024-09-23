package com.example.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDto {
	 private int id;
	   
		private BigDecimal totalPrice;
	    private String status;
	    private LocalDateTime createAt;
	   private List<OrderItemsResponseDto> items;
	   
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreateAt() {
		return createAt;
	}
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
	public List<OrderItemsResponseDto> getItems() {
		return items;
	}
	public void setItems(List<OrderItemsResponseDto> items) {
		this.items = items;
	}
	   

}
