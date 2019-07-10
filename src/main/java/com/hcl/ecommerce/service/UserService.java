package com.hcl.ecommerce.service;

import java.util.List;

import javax.management.relation.RoleNotFoundException;

import com.hcl.ecommerce.dto.ProfileDto;
import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.pojo.User;

public interface UserService {
	
	public String createUser(UserDto userDto) throws RoleNotFoundException;

	public String userLogin(String userName, String password);

	public String deleteAccount(Long id);

	public List<User> usersByRole(String name) throws Exception;

	public String updateUserProfile(Long id, ProfileDto profileDto);

}
