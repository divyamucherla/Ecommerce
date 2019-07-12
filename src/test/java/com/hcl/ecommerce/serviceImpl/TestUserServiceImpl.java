package com.hcl.ecommerce.serviceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.relation.RoleNotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.ecommerce.dto.ProfileDto;
import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.exception.PasswordMismatchException;
import com.hcl.ecommerce.exception.UserAlreadyExists;
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

		
	@Test(expected=RoleNotFoundException.class)
	public void createUserTest() throws RoleNotFoundException {

		UserDto userRequestDto = getUserRequestDto();
		Mockito.when(passwordUtil.encodePassword(userRequestDto.getPassword())).thenReturn("1234");
		Assert.assertEquals("Registration success", userServiceImpl.createUser(userRequestDto));

	}

	@Test(expected = RoleNotFoundException.class)
	public void createUser_1Test() throws RoleNotFoundException {

		UserDto userRequestDto = getUserRequestDto();
		User user = getUser();
		Mockito.when(userRepo.findByUserName(Mockito.anyString())).thenReturn(user);
		userServiceImpl.createUser(userRequestDto);
	}

	@Test(expected = RoleNotFoundException.class)
	public void createUser_2Test() throws RoleNotFoundException {
		UserDto userRequestDto = getUserRequestDto();
		userRequestDto.setConfirmPasword("123");
		userServiceImpl.createUser(userRequestDto);
	}

	@Test(expected = RoleNotFoundException.class)
	public void createUser_3Test() throws RoleNotFoundException {
		UserDto userRequestDto = getUserRequestDto();
		userRequestDto.setRole(null);
		userServiceImpl.createUser(userRequestDto);
	}

	@Test(expected = UserNotFoundException.class)
	public void userLoginTest() {
		User user = getUser();
		Mockito.when(passwordUtil.encodePassword(user.getPassword())).thenReturn("1234");
		Mockito.when(userRepo.findByUserNameAndPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Optional.of(user));
		Assert.assertEquals("Login success", userServiceImpl.userLogin("name", "1234"));
	}

	@Test(expected = UserNotFoundException.class)
	public void userLogin_1Test() {
		userServiceImpl.userLogin("name", "12344");
	}

	@Test
	public void updateUserProileTest() {
		User user = getUser();
		ProfileDto profileRequestDto = getProfileRequestDto();
		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Assert.assertEquals("Profile updated successfuly", userServiceImpl.updateUserProfile(1L, profileRequestDto));

	}

	@Test(expected = UserNotFoundException.class)
	public void updateUserProfile_1Test() {
		ProfileDto profileRequestDto = getProfileRequestDto();
		userServiceImpl.updateUserProfile(30L, profileRequestDto);
	}

	@Test
	public void deleteProfileTest() {
		User user = getUser();
		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		user.setStatus(false);
		Assert.assertEquals("success", userServiceImpl.deleteAccount(1L));
	}

	@Test(expected = UserNotFoundException.class)
	public void deleteProfile_1Test() {
		userServiceImpl.deleteAccount(8L);
	}

	@Test
	public void usersByRoleTest() throws Exception {
		User user = new User(1L, "name", "name1", "address", "1234", Role.SELLER, new Date(0), new Date(0), true);
		User user1 = new User(2L, "name", "name1", "address", "1234", Role.BUYER, new Date(0), new Date(0), true);

		List<User> users = new ArrayList<User>();
		users.add(user);
		users.add(user1);

		String role = "buyer";

		Mockito.when(userRepo.findByRole(Role.forValue(role.toUpperCase()))).thenReturn(users);
		Assert.assertEquals(users.size(), userServiceImpl.usersByRole(role).size());

	}

/*	@Test(expected = Exception.class)
	public void usersByRole1_Test() throws Exception {
		String role = null;
		userServiceImpl.usersByRole(role);
	}*/

	public UserDto getUserRequestDto() {
		UserDto userRequestDto = new UserDto("name", "name1", "address", "1234", "1234", Role.SELLER);
		return userRequestDto;
	}

	public User getUser() {
		User user = new User(1L, "name", "name1", "address", "1234", Role.SELLER, new Date(0), new Date(0), true);
		return user;
	}

	public ProfileDto getProfileRequestDto() {
		ProfileDto profileRequestDto = new ProfileDto("name", "address");
		return profileRequestDto;
	}
}

