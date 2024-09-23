package com.example.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.entity.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {
	List<CartEntity> findByUserId(int userId);

    void deleteByUserId(int userId);

}
