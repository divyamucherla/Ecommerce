package com.hcl.ecommerce.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.ecommerce.dto.ProfileDto;
import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.exception.PasswordMismatchException;
import com.hcl.ecommerce.exception.UserAlreadyExists;
import com.hcl.ecommerce.exception.UserNotFoundException;
import com.hcl.ecommerce.pojo.Role;
import com.hcl.ecommerce.pojo.User;
import com.hcl.ecommerce.repo.UserRepo;
import com.hcl.ecommerce.service.UserService;
import com.hcl.ecommerce.util.PasswordUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordUtil passwordUtil;

	@Override
	public String createUser(UserDto userDto) throws RoleNotFoundException {
		User oldUser = userRepo.findByUserName(userDto.getUserName());
		if (oldUser != null)
			throw new UserAlreadyExists(userDto.getUserName() + " already exists.");

		if (userDto.getRole() == null)
			throw new RoleNotFoundException("Role should be either buyer or seller");

		if (userDto.getConfirmPasword().equals(userDto.getPassword())) {
			User user = new User();
			user.setAddress(userDto.getAddress());
			user.setName(userDto.getName());
			user.setPassword(passwordUtil.encodePassword(userDto.getPassword()));
			user.setRole(userDto.getRole());
			user.setUserName(userDto.getUserName());
			userRepo.save(user);
			return "Registration success";
		} else {
			throw new PasswordMismatchException("Password and confirmpassword should be same");
		}

	}

	@Override
	public String userLogin(String userName, String password) {
		Optional<User> user = userRepo.findByUserNameAndPassword(userName, passwordUtil.encodePassword(password));
		if (user.isPresent() && user.get().isStatus()) {
			return "Login success";
		} else {
			throw new UserNotFoundException("Invlid username or password");
		}

	}

	/**
	 * Update the user profile
	 */

	@Override
	public String updateUserProfile(Long id, ProfileDto profileDto) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			user.get().setName(profileDto.getName());
			user.get().setAddress(profileDto.getAddress());

			userRepo.save(user.get());

			return "Profile updated successfuly";
		} else {
			throw new UserNotFoundException("user not found");
		}
	}

	/**
	 * Delete the account(status is 0(inactive))
	 */

	@Override
	public String deleteAccount(Long id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			user.get().setStatus(false);
			userRepo.save(user.get());
			return "success";
		} else {
			throw new UserNotFoundException("User not found");
		}
	}

	/**
	 * Get the users by buyers or sellers
	 * @throws Exception 
	 */

	@Override
	public List<User> usersByRole(String role) throws Exception {
		if (role == null || role.isEmpty() || role.equalsIgnoreCase("sellers") || role.equalsIgnoreCase("buyers"))
			throw new Exception("Please provide role");
		else
			return userRepo.findByRole(Role.forValue(role.toUpperCase()));

	}

}
