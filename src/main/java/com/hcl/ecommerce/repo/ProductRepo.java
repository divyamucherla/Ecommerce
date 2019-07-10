package com.hcl.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.ecommerce.pojo.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

	@Query("select p from Product p where p.category.categoryId=?1")
	List<Product> getByProduct(Long categoryId);

	List<Product> findByProductNameContainingIgnoreCase(String name);

}
