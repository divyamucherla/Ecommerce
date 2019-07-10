package com.hcl.ecommerce.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.ecommerce.dto.ProductDto;
import com.hcl.ecommerce.exception.UserNotFoundException;
import com.hcl.ecommerce.pojo.Category;
import com.hcl.ecommerce.pojo.Product;
import com.hcl.ecommerce.pojo.Role;
import com.hcl.ecommerce.pojo.User;
import com.hcl.ecommerce.repo.CategoryRepo;
import com.hcl.ecommerce.repo.ProductRepo;
import com.hcl.ecommerce.repo.UserRepo;
import com.hcl.ecommerce.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	ProductRepo productRepo;

	@Override
	public String addProduct(ProductDto productDto) throws Exception {

		Optional<User> user = userRepo.findById(productDto.getUserId());

		if (!user.isPresent() || !user.get().isStatus())
			throw new UserNotFoundException("User not found");
		if (!user.get().getRole().equals(Role.SELLER))
			throw new Exception("Sellers only add the products");

		Category category = new Category();
		Optional<Category> category1 = categoryRepo.findById(productDto.getCategoryId());

		if (!category1.isPresent())
			throw new Exception("Invalid Category");
		if (productDto.getProductPrice() <= 0)
			throw new Exception("Invalid price");

		if (productDto.getProductQuantity() <= 0)
			throw new Exception("Invalid quantity");

		
		category.setCategoryId(productDto.getCategoryId());

		Product product = new Product();
		product.setCategory(category);
		product.setProductName(productDto.getProductName());
		product.setProductPrice(productDto.getProductPrice());
		product.setProductQuantity(productDto.getProductQuantity());
		product.setCreatedUserId(productDto.getUserId());

		productRepo.save(product);
		return "Product added succesfully";
	}

	/**
	 * Get all the products
	 */

	@Override
	public List<Product> products() {
		return productRepo.findAll();
	}

	/**
	 * Get the list of the products by category
	 */
	@Override
	public List<Product> productsByCategory(Long categoryId) {

		return productRepo.getByProduct(categoryId);

	}

	@Override
	public List<Product> productsByName(String name) {
		return productRepo.findByProductNameContainingIgnoreCase(name);
	}
}
