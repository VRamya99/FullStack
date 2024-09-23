package com.example.app.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dto.CartItemResponse;
import com.example.app.entity.CartEntity;
import com.example.app.entity.ProductEntity;
import com.example.app.entity.UserEntity;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.repo.CartRepository;
import com.example.app.repo.ProductRepository;
import com.example.app.repo.UserRepository;
import com.example.app.service.CartService;

import jakarta.transaction.Transactional;
@Service
public class CartServiceImpl implements CartService {
	
	private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


	@Override
	@Transactional
	public String addToCart(int userId, int productId, int quantity) {
		 UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
	        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	        if (product.getStock() < quantity) {
	            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
	        }
	        BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(quantity));
	        CartEntity cartItem = new CartEntity(user, product, quantity, price);
	         cartRepository.save(cartItem);
	         return "product added to cart succesfully";
		
	}

	@Override

	public List<CartItemResponse> getCartItems(int userId) {
		UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        List<CartEntity> cartItems = cartRepository.findByUserId(user.getId());

        return cartItems.stream().map(cart -> {
            ProductEntity product = cart.getProduct();
            BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(cart.getQuantity()));
            return new CartItemResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    cart.getQuantity(),
                    totalPrice
            );
        }).collect(Collectors.toList());
		
	}

	@Override
	public BigDecimal calculateTotalPrice(int userId) {
		List<CartItemResponse> cartItems = getCartItems(userId);
        return cartItems.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    
	}

	@Override
	@Transactional
	public String clearCart(int userId) {
		 cartRepository.deleteByUserId(userId);
		 return "cart cleared";
		
	}

}
