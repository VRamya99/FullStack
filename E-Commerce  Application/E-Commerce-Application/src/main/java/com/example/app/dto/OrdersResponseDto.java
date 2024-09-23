package com.example.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrdersResponseDto {
	    private int id;
	   
		private BigDecimal totalPrice;
	    private String status;
	    private LocalDateTime createAt;
	   
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
		
}
