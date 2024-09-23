package com.example.app.service;

import java.util.List;

import com.example.app.entity.ProductEntity;

public interface ProductService {
	
	public String createProduct(ProductEntity product);
	public String updateProduct(int id,ProductEntity product);
	public List<ProductEntity> getProducts();
	public String deleteProduct(int id);

}
