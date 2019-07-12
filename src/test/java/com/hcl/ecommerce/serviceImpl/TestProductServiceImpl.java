package com.hcl.ecommerce.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
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

	@Test
	public void addProdutTest() throws Exception {
		ProductDto productRequestDto = getProductRequestDto();
		Category category = getCategory();
		User user = getUser();
		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Mockito.when(categoryRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
		Assert.assertEquals("Product added succesfully", productServiceImpl.addProduct(productRequestDto));

	}

	@Test(expected = UserNotFoundException.class)
	public void addProduct1_Test() throws Exception {
		ProductDto productRequestDto = getProductRequestDto();
		productRequestDto.setUserId(20L);
		productServiceImpl.addProduct(productRequestDto);
	}

	@Test(expected = Exception.class)
	public void addProduct2_Test() throws Exception {
		User user = getUser();
		user.setRole(Role.BUYER);
		ProductDto productRequestDto = getProductRequestDto();
		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		productServiceImpl.addProduct(productRequestDto);
	}

	@Test(expected = Exception.class)
	public void addProduct3_Test() throws Exception {
		ProductDto productRequestDto = getProductRequestDto();
		productRequestDto.setCategoryId(234L);
		User user = getUser();
		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		productServiceImpl.addProduct(productRequestDto);
	}

	@Test(expected = Exception.class)
	public void addProduct4_Test() throws Exception {
		ProductDto productRequestDto = getProductRequestDto();
		productRequestDto.setProductPrice(0L);
		User user = getUser();
		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Assert.assertEquals("Invalid price", productServiceImpl.addProduct(productRequestDto));
	}

	@Test(expected = Exception.class)
	public void addProduct5_Test() throws Exception {
		ProductDto productRequestDto = getProductRequestDto();
		productRequestDto.setProductQuantity(0L);
		User user = getUser();
		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Assert.assertEquals("Invalid quantity", productServiceImpl.addProduct(productRequestDto));
	}

	@Test
	public void productsTest() {
		List<Product> productLists = new ArrayList<Product>();
		Product product = getProduct();
		productLists.add(product);
		Mockito.when(productRepo.findAll()).thenReturn(productLists);
		Assert.assertEquals(productLists.size(), productServiceImpl.products().size());
	}

	@Test
	public void productsBycategory_Test() {
		Category category = getCategory();
		List<Product> productLists = new ArrayList<Product>();
		Product product = getProduct();
		productLists.add(product);
		Mockito.when(productRepo.getByProduct(Mockito.anyLong())).thenReturn(productLists);
		Assert.assertEquals(productLists.size(),
				productServiceImpl.productsByCategory(category.getCategoryId()).size());
	}

	@Test
	public void productsByNameTest() {
		List<Product> productLists = new ArrayList<Product>();
		Product product = getProduct();
		productLists.add(product);
		Mockito.when(productRepo.findByProductNameContainingIgnoreCase(Mockito.anyString())).thenReturn(productLists);
		Assert.assertEquals(productLists.size(), productServiceImpl.productsByName("sam").size());
	}

	public ProductDto getProductRequestDto() {
		ProductDto productRequestDto = new ProductDto("Samsung", 23490.00, 20L, 1L, 3L);
		return productRequestDto;
	}

	public Product getProduct() {
		Product product = new Product(1L, "Samsung", 23490.00, 20L, 3L, new Date(), null);
		return product;
	}

	public Category getCategory() {
		Category category = new Category(1L, "Laptops", null);
		return category;
	}

	public User getUser() {
		User user = new User();
		user.setAddress("address");
		user.setName("name");
		user.setPassword("password");
		user.setRole(Role.SELLER);
		user.setStatus(true);
		user.setUserId(1L);
		user.setUserName("userName");
		return user;
	}

}
