package com.hcl.ecommerce.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
import com.hcl.ecommerce.dto.ProfileDto;
import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.pojo.User;
import com.hcl.ecommerce.service.UserService;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { TestContext.class, UserController.class })
@WebAppConfiguration
public class TestUserController {

	@InjectMocks
	UserController userController;

	private MockMvc mockMvc;

	@Mock
	UserService userService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testuserRegisteration() throws JsonProcessingException, Exception {
		UserDto userDto = new UserDto();
		String str = "value";
		ResponseEntity<String> st = new ResponseEntity<String>(str, HttpStatus.CREATED);
		Mockito.when(userService.createUser(Mockito.anyObject())).thenReturn(str);
		this.mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userDto)))
				.andReturn();
		ResponseEntity<String> st1 = userController.userRegisteration(userDto);
		assertEquals(st1, st);
	}

	@Test
	public void testuserLogin() throws JsonProcessingException, Exception {
		String userName = "name";
		String password = "password";
		String str = "value";
		ResponseEntity<String> st = new ResponseEntity<String>(str, HttpStatus.OK);
		Mockito.when(userService.userLogin(Mockito.anyString(), Mockito.anyString())).thenReturn("value");

		this.mockMvc.perform(
				get("/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(userName).concat(password)))
				.andReturn();

		ResponseEntity<String> st1 = userController.userLogin(userName, password);
		assertEquals(st1, st);
	}

	@Test
	public void testupdateUserProfile() throws JsonProcessingException, Exception {
		Long id = 1L;
		ProfileDto profileDto = new ProfileDto();
		String str = "value";
		ResponseEntity<String> st = new ResponseEntity<String>(str, HttpStatus.OK);

		Mockito.when(userService.updateUserProfile(Mockito.anyLong(), Mockito.anyObject())).thenReturn(str);

		this.mockMvc.perform(put("/{id}", 1L).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(asJsonString(id).concat(asJsonString(profileDto)))).andReturn();

		ResponseEntity<String> st1 = userController.updateUserProfile(id, profileDto);
		assertEquals(st1, st);
	}

	@Test
	public void testdeleteAccount() throws JsonProcessingException, Exception {
		Long id = 1L;
		String str = "value";
		ResponseEntity<String> st = new ResponseEntity<String>(str, HttpStatus.OK);

		Mockito.when(userService.deleteAccount(Mockito.anyLong())).thenReturn(str);
		this.mockMvc.perform(delete("/{id}", 1L).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(asJsonString(id).concat(asJsonString(id)))).andReturn();

		ResponseEntity<String> st1 = userController.deleteAccount(id);
		assertEquals(st1, st);
	}

	@Test
	public void testusersByRole() throws Exception {
		String name = "name";
		List<User> li = new ArrayList<>();
		User user = new User();
		user.setAddress("address");
		user.setName("name");
		li.add(user);
		ResponseEntity<List<User>> res = new ResponseEntity<List<User>>(li, HttpStatus.OK);
		Mockito.when(userService.usersByRole(Mockito.anyString())).thenReturn(li);

		this.mockMvc.perform(get("/role/{name}",name).contentType(MediaType.APPLICATION_JSON).content(asJsonString(name)))
				.andReturn();

		ResponseEntity<List<User>> res1 =userController.usersByRole(name);
		assertEquals(res1, res);
	}

	public static String asJsonString(final Object obj) throws JsonProcessingException {

		return new ObjectMapper().writeValueAsString(obj);
	}

}
