package com.example.app.service.impl;

import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;

import com.example.app.entity.ProductEntity;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.repo.ProductRepository;
import com.example.app.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService{
	
	private ProductRepository productRepository;
	
	ProductServiceImpl(ProductRepository productRepository)
	{
		this.productRepository=productRepository;
	}

	@Override
	public String createProduct(ProductEntity product) {
		productRepository.save(product);
		
		return "Created New Product";
	}

	@Override
	public String updateProduct(int id, ProductEntity product) {
	ProductEntity prod=productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product with id "+id+" is Not Found"));
	
	if(product.getName()!=null)
	{
		prod.setName(product.getName());
		
	}
	if(product.getDescription()!=null)
	{
		prod.setDescription(product.getDescription());
		
	}
	if(product.getPrice()!=null)
	{
		prod.setPrice(product.getPrice());
		
	}
	if(product.getStock()!=0)
	{
		prod.setStock(product.getStock());
		
	}
	productRepository.save(prod);
	return "Product with id "+id+" successfully updated";
	
	}

	@Override
	public List<ProductEntity> getProducts() {
		
		return productRepository.findAll();
		
	}

	@Override
	public String deleteProduct(int id) {
		
		productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product with id "+id+" is Not Found"));
		productRepository.deleteById(id);
		return "Product with id "+id+" successfully deleted";
		
	}
	
	
	

}
