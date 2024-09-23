package com.example.app.jwt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
	
	private JwtValidationFilter jwtValidationFilter;
	@Autowired
	public SecurityConfig(JwtValidationFilter jwtValidationFilter) {
		this.jwtValidationFilter=jwtValidationFilter;
	}
@Bean
SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
	 http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	 http.csrf(csrf->csrf.disable());
	 http
     .cors(Customizer.withDefaults());
	 http
     .authorizeHttpRequests(authorizeRequests ->
     authorizeRequests
    
     .requestMatchers(HttpMethod.POST, "/v3/register", "/v3/login").permitAll()

     
     .requestMatchers(HttpMethod.GET, "/v2/products").authenticated() 
     .requestMatchers(HttpMethod.POST, "/v2/products").hasRole("ADMIN")
     .requestMatchers(HttpMethod.PUT, "/v2/products/{id}").hasRole("ADMIN")
     .requestMatchers(HttpMethod.DELETE, "/v2/products/{id}").hasRole("ADMIN")
     .requestMatchers(HttpMethod.POST, "/v4/add").authenticated()
     .requestMatchers(HttpMethod.GET, "/v4/items").authenticated()
     .requestMatchers(HttpMethod.GET, "/v4/total").authenticated()
     .requestMatchers(HttpMethod.DELETE,"/v4/delete").authenticated()
     .requestMatchers(HttpMethod.POST, "/v1/orders").authenticated()
     .requestMatchers(HttpMethod.GET, "/v1/orders").authenticated()
     .requestMatchers(HttpMethod.GET, "v1/orders/{id}").authenticated()

     .anyRequest().authenticated()).addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);
	 http.formLogin(Customizer.withDefaults());
     http.httpBasic(Customizer.withDefaults());
     return http.build();
}
@Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:4200"));
    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    corsConfiguration.setExposedHeaders(List.of("Authorization")); 
    corsConfiguration.setAllowCredentials(true); 

    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
}
@Bean
 PasswordEncoder passwordEncoder()
{
	return new BCryptPasswordEncoder();
}


}
