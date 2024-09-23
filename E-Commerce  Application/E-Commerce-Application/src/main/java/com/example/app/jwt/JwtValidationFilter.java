package com.example.app.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.app.entity.UserEntity;
import com.example.app.repo.UserRepository;
import com.example.app.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtValidationFilter extends OncePerRequestFilter {
	
	private JwtService jwtService;
	private UserService userService;
	private UserRepository userRepository;
	
	@Autowired
	public JwtValidationFilter(JwtService jwtService,UserService userService,UserRepository userRepository) {
		this.jwtService=jwtService;
		this.userService=userService;
		this.userRepository=userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 String authHeader=request.getHeader("Authorization");
		 String userName=null;
		 String jwt=null;
		 if(authHeader!=null && authHeader.startsWith("Bearer" )) {
			 jwt=authHeader.substring(7);
			 userName=jwtService.getUserName(jwt);
			 
		 
		 if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		 {
			 if(jwtService.isTokenValid(userName, jwt)) {
				 
				 UserEntity user=userRepository.findByUserName(userName);
				 List<GrantedAuthority> authorities=new ArrayList<>();
				 authorities.add(new SimpleGrantedAuthority(user.getRole()));
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userName,null,authorities);
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		 }
		 }
		 }
		 filterChain.doFilter(request, response);
		
	}
	

}
