package com.hcl.ecommerce.serviceImpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.ecommerce.dto.ProductDto;
import com.hcl.ecommerce.exception.UserNotFoundException;
import com.hcl.ecommerce.pojo.Category;
import com.hcl.ecommerce.pojo.Product;
import com.hcl.ecommerce.pojo.Role;
import com.hcl.ecommerce.pojo.User;
import com.hcl.ecommerce.repo.CategoryRepo;
import com.hcl.ecommerce.repo.ProductRepo;
import com.hcl.ecommerce.repo.UserRepo;

@RunWith(MockitoJUnitRunner.class)
public class TestProductServiceImpl {

	@InjectMocks
	ProductServiceImpl productServiceImpl;

	@Mock
	UserRepo userRepo;

	@Mock
	CategoryRepo categoryRepo;

	@Mock
	ProductRepo productRepo;

	@Test(expected=UserNotFoundException.class)
	public void testaddProduct() throws Exception {
		ProductDto productDto = new ProductDto();
		productDto.setProductPrice(new Double(10));
		productDto.setProductQuantity(1L);
		Product product = new Product();
		product.setProductId(1L);
		product.setProductQuantity(1L);
		product.setProductPrice(new Double(10));
		product.setProductName("productName");
		Category catogery = new Category();
		catogery.setCategoryId(1L);
		product.setCategory(catogery);
		User user = new User();
		user.setStatus(true);
		user.setRole(Role.SELLER);
		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Mockito.when(productRepo.save(Mockito.anyObject())).thenReturn(product);
		Mockito.when(categoryRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(catogery));

		String str = productServiceImpl.addProduct(productDto);
		assertEquals(new UserNotFoundException("User not found"), str);
	}
	
	@Test
	public void testproducts() {
		List<Product> li = new ArrayList<>();
		Product product = new Product();
		product.setProductId(1L);
		product.setProductQuantity(1L);
		li.add(product);
		Mockito.when(productRepo.findAll()).thenReturn(li);
		List<Product> li1=productServiceImpl.products();
		assertEquals(li1, li);
	}
	
	@Test
	public void testproductsByCategory() {
		List<Product> li = new ArrayList<>();
		Product product = new Product();
		product.setProductId(1L);
		product.setProductQuantity(1L);
		li.add(product);
		Mockito.when(productRepo.getByProduct(Mockito.anyLong())).thenReturn(li);
		List<Product> li1=productServiceImpl.productsByCategory(1L);
		assertEquals(li1, li);
	}
	
	@Test
	public void testproductsByName() {
		List<Product> li = new ArrayList<>();
		Product product = new Product();
		product.setProductId(1L);
		product.setProductQuantity(1L);
		product.setProductName("name");
		li.add(product);
		Mockito.when(productRepo.findByProductNameContainingIgnoreCase(Mockito.anyString())).thenReturn(li);
		List<Product> li1=productServiceImpl.productsByName("name");
		assertEquals(li1, li);	
		
	}

}
