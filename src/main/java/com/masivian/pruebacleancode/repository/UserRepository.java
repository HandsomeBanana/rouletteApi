package com.masivian.pruebacleancode.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masivian.pruebacleancode.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	
	
}
