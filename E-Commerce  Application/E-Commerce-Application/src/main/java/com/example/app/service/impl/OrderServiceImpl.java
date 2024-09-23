package com.example.app.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.app.dto.OrderItemRequest;
import com.example.app.dto.OrderItemsResponseDto;
import com.example.app.dto.OrderRequest;
import com.example.app.dto.OrderResponseDto;
import com.example.app.dto.OrdersResponseDto;
import com.example.app.dto.ProductDto;

import com.example.app.entity.OrderEntity;
import com.example.app.entity.OrderItemEntity;
import com.example.app.entity.ProductEntity;
import com.example.app.entity.UserEntity;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.repo.OrderRepository;
import com.example.app.repo.ProductRepository;
import com.example.app.repo.UserRepository;
import com.example.app.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService{
	
	private OrderRepository orderRepository;
	
	private UserRepository userRepository;
	private ProductRepository productRepository;
	
	OrderServiceImpl(OrderRepository orderRepository,ProductRepository productRepository, UserRepository userRepository)
	{
		this.orderRepository=orderRepository;
		this.productRepository=productRepository;
		this.userRepository= userRepository;
	}

	@Override
	public String createOrder(OrderRequest orderRequest,int userId) {
		UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

	    OrderEntity order = new OrderEntity();
	    order.setUser(user); 
	    order.setStatus("Pending");
	    order.setCreateAt(LocalDateTime.now());
	    BigDecimal totalPrice = BigDecimal.ZERO;

	    for (OrderItemRequest itemRequest : orderRequest.getOrderItems()) {
	        OrderItemEntity orderItem = new OrderItemEntity();
	        ProductEntity product = productRepository.findById(itemRequest.getProductId())
	                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	        orderItem.setProduct(product);
	        orderItem.setQuantity(itemRequest.getQuantity());
	        orderItem.setPrice(product.getPrice().multiply(new BigDecimal(itemRequest.getQuantity())));
	        
	        totalPrice = totalPrice.add(orderItem.getPrice());
	        order.addOrderItem(orderItem);
	    }

	    order.setTotalPrice(totalPrice);
	    orderRepository.save(order);
	    return "Order created successfully";
	}

	
    @Override
    public List<OrdersResponseDto> getOrders(int userId) {
        List<OrderEntity> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToOrdersResponseDto)
                .collect(Collectors.toList());
    }

   
    private OrdersResponseDto convertToOrdersResponseDto(OrderEntity orderEntity) {
        OrdersResponseDto dto = new OrdersResponseDto();
        dto.setId(orderEntity.getId());
        dto.setTotalPrice(orderEntity.getTotalPrice());
        dto.setStatus(orderEntity.getStatus());
        dto.setCreateAt(orderEntity.getCreateAt());
        return dto;
    }

   
    @Override
    public OrderResponseDto getOrder(int order_id) {
        OrderEntity order = orderRepository.findById(order_id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found with id " + order_id));

        return convertToOrderResponseDto(order); 
    }

   
    private OrderResponseDto convertToOrderResponseDto(OrderEntity orderEntity) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(orderEntity.getId());
        dto.setTotalPrice(orderEntity.getTotalPrice());
        dto.setStatus(orderEntity.getStatus());
        dto.setCreateAt(orderEntity.getCreateAt());

        // Convert order items
        List<OrderItemsResponseDto> orderItems = orderEntity.getItems().stream().map(this::convertToOrderItemsResponseDto).collect(Collectors.toList());
        dto.setItems(orderItems);
        
        return dto;
    }

    private OrderItemsResponseDto convertToOrderItemsResponseDto(OrderItemEntity orderItemEntity) {
        OrderItemsResponseDto dto = new OrderItemsResponseDto();
        dto.setId(orderItemEntity.getId());
        dto.setQuantity(orderItemEntity.getQuantity());
        dto.setPrice(orderItemEntity.getPrice());

        // Convert product details
        ProductDto productDto = convertToProductDto(orderItemEntity.getProduct());
      
        dto.setProduct(productDto);

        return dto;
    }

    private ProductDto convertToProductDto(ProductEntity productEntity) {
        ProductDto dto = new ProductDto();
        dto.setId(productEntity.getId());
        dto.setName(productEntity.getName());
        dto.setDescription(productEntity.getDescription());
        dto.setPrice(productEntity.getPrice());

        return dto;
    }


}
