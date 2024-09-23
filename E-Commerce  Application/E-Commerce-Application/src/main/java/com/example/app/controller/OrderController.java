package com.example.app.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.dto.OrderRequest;
import com.example.app.dto.OrderResponseDto;
import com.example.app.dto.OrdersResponseDto;
import com.example.app.entity.OrderEntity;
import com.example.app.jwt.JwtService;
import com.example.app.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;



@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class OrderController {
	
	
	private OrderService orderService;
	private JwtService jwtService;
	public OrderController(OrderService orderService,JwtService jwtService) {
	this.orderService=orderService;
	this.jwtService=jwtService;
	}
	@PostMapping("/orders")
	public ResponseEntity<String> createOrder(@RequestBody OrderRequest order,HttpServletRequest request)
	{
		 String token = request.getHeader("Authorization").substring(7);
		  
		  System.out.println(token);
		int userId=jwtService.getUserId(token);
		
		return new ResponseEntity<String>(orderService.createOrder(order,userId),HttpStatus.CREATED);
		
	}
	@GetMapping("/orders")
	public ResponseEntity<List<OrdersResponseDto>> getAllOrders(HttpServletRequest request)
	{
		 String token = request.getHeader("Authorization").substring(7);
		  
		  System.out.println(token);
		int userId=jwtService.getUserId(token);
		return new ResponseEntity<List<OrdersResponseDto>>(orderService.getOrders(userId),HttpStatus.OK);
	}
	@GetMapping("/orders/{id}")
	public ResponseEntity<OrderResponseDto> getOrder(@PathVariable("id") int id)
	{
		System.out.println(id);
		return new ResponseEntity<OrderResponseDto>(orderService.getOrder(id),HttpStatus.OK);
	}
	
}
