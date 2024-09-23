package com.example.app.dto;


import java.math.BigDecimal;

public class CartItemResponse {

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;
    
	public CartItemResponse(int id, String name, String description, BigDecimal price, Integer quantity,
			BigDecimal totalPrice) {
		super();
		this.id=id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
	public CartItemResponse() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

   
   
}

