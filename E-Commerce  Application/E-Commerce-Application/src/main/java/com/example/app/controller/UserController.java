package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.dto.User;
import com.example.app.entity.UserEntity;
import com.example.app.jwt.JwtService;
import com.example.app.service.UserService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/v3")
@CrossOrigin(origins = "*")
public class UserController {
	
	private UserService userService;
	
	
	
	UserController(UserService userService)
	{
		this.userService=userService;
		
	}
	@PostMapping("/register")
	public ResponseEntity<String> createUser(@Valid @RequestBody UserEntity user)
	{
		return new  ResponseEntity<String>(userService.registerUser(user),HttpStatus.CREATED);
	}
	@PostMapping("/login")
	public ResponseEntity<String>authenticateUser(@RequestBody User user) throws Exception{
		System.out.println(user.getPassword());
		System.out.println(user.getUserName());
		return new ResponseEntity<String>(userService.login(user.getUserName(), user.getPassword()),HttpStatus.CREATED);
		
	}

}
