package com.example.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer>{

	List<OrderEntity> findByUserId(int userId);

}
