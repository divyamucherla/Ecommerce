package com.hcl.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.ecommerce.dto.ProductDto;
import com.hcl.ecommerce.pojo.Product;
import com.hcl.ecommerce.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/create")
	public ResponseEntity<String> addProduct(@RequestBody ProductDto prouductDto) throws Exception {
		return new ResponseEntity<>(productService.addProduct(prouductDto), HttpStatus.CREATED);

	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> products() {
		return new ResponseEntity<>(productService.products(), HttpStatus.OK);

	}

	@GetMapping("/category/{id}")
	public ResponseEntity<List<Product>> productsByCategory(@PathVariable Long id) {
		return new ResponseEntity<>(productService.productsByCategory(id), HttpStatus.OK);
	}
	
	
	@GetMapping("/{name}")
	public ResponseEntity<List<Product>> productsByName(@PathVariable String name) {
		return new ResponseEntity<>(productService.productsByName(name), HttpStatus.OK);
	}


}
