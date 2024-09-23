package com.example.app.jwt;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {
	
	@Value("${secretKey}")
	private String secretKey;
	
	public String generateToken(String userName,String role,int userId)
	{
		Map<String, Object> claims=new HashMap<>();
		claims.put("role", role);
		claims.put("userId", userId);
		
		return createToken(userName,claims);
	}
	public String createToken(String userName,Map<String, Object> claims)
	{
		String jwt=Jwts.builder().setClaims(claims).
				setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis())).
				setExpiration(new Date(System.currentTimeMillis()+1000*30*60)).
				signWith(SignatureAlgorithm.HS512,secretKey).compact();
		return jwt;
	}
	public Claims getClaims(String token)
	{
		
		
	return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
	
	public String getUserName(String token)
	{
		return getClaims(token).getSubject();
	}
	public int getUserId(String token) {
	    Claims claims = getClaims(token);
	    return claims.get("userId",Integer.class);
	}

	
	public Date extractExpiration(String token)
	{
		return getClaims(token).getExpiration();
	}
	public boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}
	public boolean isTokenValid(String userName,String token)
	{
		return userName.matches(getUserName(token)) && !isTokenExpired(token);
		
	}


	

}
