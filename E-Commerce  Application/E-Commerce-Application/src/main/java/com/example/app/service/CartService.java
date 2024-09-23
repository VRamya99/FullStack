package com.example.app.service;

import java.math.BigDecimal;
import java.util.List;

import com.example.app.dto.CartItemResponse;
import com.example.app.entity.CartEntity;


public interface CartService {
	
	public String addToCart(int userId,int productId,int quantity);
	
	public List<CartItemResponse> getCartItems(int user_id);
	 public BigDecimal calculateTotalPrice(int userId);
	 public String clearCart(int userId);

}
