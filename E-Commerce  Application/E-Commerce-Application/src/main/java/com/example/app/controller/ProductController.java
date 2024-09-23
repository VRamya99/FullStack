package com.example.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.app.entity.ProductEntity;

import com.example.app.service.ProductService;
@RestController
@RequestMapping("/v2")
@CrossOrigin(origins = "*")
public class ProductController {

private ProductService productService;
	
 ProductController(ProductService productService)
	{
		this.productService=productService;
	}
	@PostMapping("/products")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addProduct(@RequestBody ProductEntity product)
	{
		return new ResponseEntity<String>(productService.createProduct(product),HttpStatus.CREATED);
	}
	@PutMapping("/products/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> updateProduct(@PathVariable("id") int id,@RequestBody ProductEntity product)
	{
		return new ResponseEntity<String>(productService.updateProduct(id,product),HttpStatus.CREATED);
	}
	@GetMapping("/products")
	public ResponseEntity<List<ProductEntity>> getProduct()
	{
		return new ResponseEntity<List<ProductEntity>>(productService.getProducts(),HttpStatus.OK);
	}
	@DeleteMapping("/products/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int id)
	{
		return new ResponseEntity<String>(productService.deleteProduct(id),HttpStatus.OK);
	}


	
}
