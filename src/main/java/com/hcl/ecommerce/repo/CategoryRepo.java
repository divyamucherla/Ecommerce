package com.hcl.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.ecommerce.pojo.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>{

}
