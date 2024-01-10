package com.example.demo.config;

import java.io.IOException;
import java.util.Set;

import javax.persistence.criteria.From;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.build.AllowSysOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component; 	
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.implementation.JwtService;
import com.example.demo.service.implementation.UserDetailServiceImpl;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;



public class JwtAuthenticationFilter extends OncePerRequestFilter{
	

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
		
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
			if (request.getServletPath().contains("/api/auth/login") || request.getServletPath().contains("/api/auth/register")) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String jwt = jwtService.parseJwt(request);
			
			String username = jwtService.getUsernameFromJwtToken(jwt);
			
			if (username != null) {
				UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);
				
				if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
					
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(auth);
					
				}
			}
						
		}catch (UsernameNotFoundException e) {
			logger.error("Username not found: {} ", e.getMessage());
		} catch (Exception e) {
			logger.error("Error: {}", e.getMessage());
		}
		filterChain.doFilter(request, response);
	}
	

}
