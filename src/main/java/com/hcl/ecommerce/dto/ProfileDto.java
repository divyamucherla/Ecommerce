package com.hcl.ecommerce.dto;

import javax.validation.constraints.NotEmpty;

public class ProfileDto {

	@NotEmpty(message = "Name cannot be empty")
	private String name;
	@NotEmpty(message = "Address cannot be empty")
	private String address;

	public ProfileDto(String string, String string2) {
		// TODO Auto-generated constructor stub
	}

	public ProfileDto() {
		// TODO Auto-generated constructor stub
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

}
