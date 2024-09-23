package com.example.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.entity.UserEntity;



public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	public UserEntity findByUserName(String userName);
	
}
