package com.example.app.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.dto.CartItemResponse;
import com.example.app.entity.CartEntity;
import com.example.app.jwt.JwtService;
import com.example.app.service.CartService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v4")
public class CartController {
	
	
	private CartService cartService;
	private JwtService jwtService;
	CartController(CartService cartService,JwtService jwtService)
	{
		this.cartService=cartService;
		this.jwtService=jwtService;
	}
	
	 @PostMapping("/add")
	 public ResponseEntity<String> addToCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity,HttpServletRequest request) {
		 System.out.println(productId);
		 System.out.println(quantity);
		 String token = request.getHeader("Authorization").substring(7);
		  
		  System.out.println(token);
		  int userId=jwtService.getUserId(token);
		  System.out.println(userId);
	        return new ResponseEntity<String>(cartService.addToCart(userId, productId, quantity),HttpStatus.CREATED);
	    }

	 @GetMapping("/items")
	 public ResponseEntity<List<CartItemResponse>> getAllItems(HttpServletRequest request){
	
		 String token = request.getHeader("Authorization").substring(7);
		  
		  System.out.println(token);
		  int userId=jwtService.getUserId(token);
	        List<CartItemResponse> cartItem = cartService.getCartItems(userId);
	        return new ResponseEntity<List<CartItemResponse>>(cartItem,HttpStatus.OK);
	    }
	 @GetMapping("/total")
	 public ResponseEntity<BigDecimal> totalProce(@RequestParam int userId) {
	        BigDecimal totalPrice = cartService.calculateTotalPrice(userId);
	        return new ResponseEntity<BigDecimal>(totalPrice,HttpStatus.CREATED);
	    }
	 @DeleteMapping("/delete")
	 public ResponseEntity<String> deleteCart(HttpServletRequest request) {
		 String token = request.getHeader("Authorization").substring(7);
		  
		  System.out.println(token);
		  int userId=jwtService.getUserId(token);
	        
	        return new ResponseEntity<String>(cartService.clearCart(userId),HttpStatus.OK);
	    }



}
