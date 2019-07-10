package com.hcl.ecommerce.controller;

import java.util.List;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.ecommerce.dto.ProfileDto;
import com.hcl.ecommerce.dto.UserDto;
import com.hcl.ecommerce.pojo.User;
import com.hcl.ecommerce.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> userRegisteration(@RequestBody UserDto userDto) throws RoleNotFoundException {
		return new ResponseEntity<String>(userService.createUser(userDto), HttpStatus.CREATED);
	}

	@GetMapping("/login")
	public ResponseEntity<String> userLogin(@RequestHeader(value = "userName") String userName,
			@RequestHeader(value = "password") String password) {
		return new ResponseEntity<>(userService.userLogin(userName, password), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateUserProfile(@PathVariable Long id, @RequestBody ProfileDto profileDto) {
		return new ResponseEntity<>(userService.updateUserProfile(id, profileDto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
		return new ResponseEntity<>(userService.deleteAccount(id), HttpStatus.OK);
	}

	@GetMapping("/role/{name}")
	public ResponseEntity<List<User>> usersByRole(@PathVariable String name) throws Exception {
		return new ResponseEntity<>(userService.usersByRole(name), HttpStatus.OK);
	}

}
