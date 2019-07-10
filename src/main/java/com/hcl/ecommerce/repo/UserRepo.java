package com.hcl.ecommerce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.ecommerce.pojo.Role;
import com.hcl.ecommerce.pojo.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	public User findByUserName(String userName);

	public Optional<User> findByUserNameAndPassword(String userName, String encodePassword);

	public List<User> findByRole(Role forValue);

}
