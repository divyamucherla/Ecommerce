package com.hcl.ecommerce.serviceImpl;

import java.sql.Date;
import java.util.Optional;

import javax.management.relation.RoleNotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.exception.UserNotFoundException;
import com.hcl.ecommerce.pojo.Role;
import com.hcl.ecommerce.pojo.User;
import com.hcl.ecommerce.repo.UserRepo;
import com.hcl.ecommerce.util.PasswordUtil;

@RunWith(MockitoJUnitRunner.class)
public class TestUserServiceImpl {
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserRepo userRepo;

	@Mock
	PasswordUtil passwordUtil;

	
	
	@Test(expected= RoleNotFoundException.class)
	public void testcreateUser() throws RoleNotFoundException {
		UserDto userDto= getUserRequestDto();
		Mockito.when(passwordUtil.encodePassword(userDto.getPassword())).thenReturn("1");
		userServiceImpl.createUser(userDto);
	}
	
	public UserDto getUserRequestDto() {
		UserDto userRequestDto = new UserDto("name", "name1", "address", "1", "1", Role.SELLER);
		return userRequestDto;
	}
	
	
	@Test(expected=UserNotFoundException.class)
	public void userLoginTest() {
		User user = getUser();
		Mockito.when(passwordUtil.encodePassword(user.getPassword())).thenReturn("password");
		Mockito.when(userRepo.findByUserNameAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Optional.of(user));
		Assert.assertEquals("Login success", userServiceImpl.userLogin("name", "password"));
	}
	
	@Test(expected = UserNotFoundException.class)
	public void userLogin_1Test() {
		userServiceImpl.userLogin("name", "password");
	}

	private User getUser() {
			User user = new User(1L, "name", "name1", "address", "1234", Role.SELLER, new Date(0), new Date(0), true);
			return user;
		}
	}

