package com.example.app.entity;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

@Entity
@Table(name = "orderItems")
public class OrderItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name="price")
    private BigDecimal price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id",nullable = false)
    private OrderEntity order;
    
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "product_id",nullable = false)
   private ProductEntity product;
   

public OrderItemEntity() {
	super();
}


public OrderItemEntity(int quantity, BigDecimal price, OrderEntity order, ProductEntity product) {
	super();
	this.quantity = quantity;
	this.price = price;
	this.order = order;
	this.product = product;
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public int getQuantity() {
	return quantity;
}


public void setQuantity(int quantity) {
	this.quantity = quantity;
}


public BigDecimal getPrice() {
	return price;
}


public void setPrice(BigDecimal price) {
	this.price = price;
}


public OrderEntity getOrder() {
	return order;
}


public void setOrder(OrderEntity order) {
	this.order = order;
}


public ProductEntity getProduct() {
	return product;
}


public void setProduct(ProductEntity product) {
	this.product = product;
}
   
   
   
	
}
