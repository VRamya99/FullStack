package com.example.app.service;

import com.example.app.entity.UserEntity;

public interface UserService {
	
	public String registerUser(UserEntity user);
	
	public String login(String userName,String password) throws Exception;

}
