package com.hcl.ecommerce.service;

import java.util.List;

import com.hcl.ecommerce.dto.ProductDto;
import com.hcl.ecommerce.pojo.Product;

public interface ProductService {

	public String addProduct(ProductDto productDto) throws Exception;
	
	public List<Product> products() ;
	
	public List<Product> productsByCategory(Long categoryId);
	
	public List<Product> productsByName(String name);

}
