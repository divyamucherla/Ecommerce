package com.hcl.ecommerce.dto;

import javax.validation.constraints.NotEmpty;

import com.hcl.ecommerce.pojo.Role;

public class UserDto {

	@NotEmpty(message = "Username cannot be empty")
	private String userName;

	@NotEmpty(message = "Name cannot be empty")
	private String name;

	@NotEmpty(message = "Address cannot be empty")
	private String address;
	
	private Role role;

	public UserDto(String string, String string2, String string3, String string4, String string5, Role seller) {
		// TODO Auto-generated constructor stub
	}

	public UserDto() {
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPasword() {
		return confirmPasword;
	}

	public void setConfirmPasword(String confirmPasword) {
		this.confirmPasword = confirmPasword;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@NotEmpty(message = "Password cannot be empty")
	private String password;

	@NotEmpty(message = "ConfirmPasword cannot be empty")
	private String confirmPasword;


}
