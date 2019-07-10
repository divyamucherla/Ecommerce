package com.hcl.ecommerce.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.ecommerce.dto.ProductDto;
import com.hcl.ecommerce.pojo.Product;
import com.hcl.ecommerce.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { TestContext.class, ProductController.class })
@WebAppConfiguration
public class TestProductController {

	@InjectMocks
	ProductController productController;

	private MockMvc mockMvc;

	@Mock
	ProductService productService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

	}

	@Test
	public void testProduct() throws Exception {
		ProductDto prouductDto = new ProductDto();
		String str = "value";
		ResponseEntity<String> st = new ResponseEntity<String>(str, HttpStatus.CREATED);
		Mockito.when(productService.addProduct(Mockito.anyObject())).thenReturn("value");
		this.mockMvc.perform(post("/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(prouductDto)))
				.andReturn();
		ResponseEntity<String> st1 = productController.addProduct(prouductDto);
		assertEquals(st1, st);
	}

	@Test
	public void testproducts() throws JsonProcessingException, Exception {
		List<Product> li = new ArrayList<>();
		Product product = new Product();
		product.setProductId(1L);
		product.setProductQuantity(1L);
		li.add(product);
		ResponseEntity<List<Product>> res = new ResponseEntity<List<Product>>(li, HttpStatus.OK);
		Mockito.when(productService.products()).thenReturn(li);
		this.mockMvc.perform(get("/products").contentType(MediaType.APPLICATION_JSON).content(asJsonString(li)))
				.andReturn();
		ResponseEntity<List<Product>> res1 = productController.products();
		assertEquals(res1, res);
	}

	@Test
	public void testproductsByCategory() throws JsonProcessingException, Exception {
		long id = 1;
		List<Product> li = new ArrayList<>();
		Product product = new Product();
		product.setProductId(1L);
		;
		product.setProductQuantity(1L);
		li.add(product);
		ResponseEntity<List<Product>> res = new ResponseEntity<List<Product>>(li, HttpStatus.OK);
		Mockito.when(productService.productsByCategory(Mockito.anyLong())).thenReturn(li);
		this.mockMvc.perform(get("/category/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(asJsonString(id)))
				.andReturn();
		ResponseEntity<List<Product>> res1 = productController.productsByCategory(id);
		assertEquals(res1, res);
	}

	@Test
	public void testproductsByName() throws JsonProcessingException, Exception {
		String name = "name";
		List<Product> li = new ArrayList<>();
		Product product = new Product();
		product.setProductId(1L);
		;
		product.setProductQuantity(1L);
		product.setProductName("productName");
		li.add(product);
		ResponseEntity<List<Product>> res = new ResponseEntity<List<Product>>(li, HttpStatus.OK);
		this.mockMvc.perform(get("/{name}", "name").contentType(MediaType.APPLICATION_JSON).content(asJsonString(name)))
				.andReturn();
		ResponseEntity<List<Product>> res1 = productController.productsByName(name);
		//assertEquals(res1, res);
	}

	public static String asJsonString(final Object obj) throws JsonProcessingException {

		return new ObjectMapper().writeValueAsString(obj);
	}

}
