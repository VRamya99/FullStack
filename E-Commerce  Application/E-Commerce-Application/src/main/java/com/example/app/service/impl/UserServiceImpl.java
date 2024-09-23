package com.example.app.service.impl;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.app.entity.UserEntity;
import com.example.app.jwt.JwtService;
import com.example.app.repo.UserRepository;
import com.example.app.service.UserService;
@Service
public class UserServiceImpl implements UserService,UserDetailsService {
	
	private UserRepository userRepository;
	private JwtService jwtService;
	

	@Autowired
	public UserServiceImpl(UserRepository userRepository,JwtService jwtService) {
		this.userRepository=userRepository;
		this.jwtService=jwtService;
		
		
	}

	@Override
	public String registerUser(UserEntity user) {
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return "User Successfully Registered";
	}

	@Override
	public String login(String userName, String password) throws Exception {
		String jwt=null;
		UserEntity user=userRepository.findByUserName(userName);
		if(user!=null)
		{
			if(user.getPassword().equals(password))
			{
				System.out.println(user.getId());
		     jwt=jwtService.generateToken(userName,user.getRole(),user.getId());
			}
		}
		else {
			throw new Exception("Invalid credentials");
		}
		return jwt;
	
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUserName(username);
	         if(user!=null)
	         {
	        	 List<GrantedAuthority> authorities=new ArrayList<>();
	        	 authorities.add(new SimpleGrantedAuthority(user.getRole()));
	        	 return new User(username, user.getPassword(),authorities );
	         }else {
	        	 throw new UsernameNotFoundException("user not found");
	         }
	}

}
