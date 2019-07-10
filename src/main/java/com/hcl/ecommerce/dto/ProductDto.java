package com.hcl.ecommerce.dto;

import javax.validation.constraints.NotEmpty;

public class ProductDto {

	@NotEmpty(message = "Product name cannot be empty")
	private String productName;
	private double productPrice;
	private Long productQuantity;
	private Long categoryId;
	private Long userId;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public Long getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ProductDto(@NotEmpty(message = "Product name cannot be empty") String productName,
			double productPrice, Long productQuantity, Long categoryId, Long userId) {
		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.productQuantity = productQuantity;
		this.categoryId = categoryId;
		this.userId = userId;
	}

	public ProductDto() {
	}

}
