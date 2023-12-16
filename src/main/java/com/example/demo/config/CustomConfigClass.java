package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomConfigClass implements LogoutHandler{

	private static Logger logger = LoggerFactory.getLogger(CustomConfigClass.class);
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		try {
			System.out.println("masuk logout");
			SecurityContextHolder.clearContext();
			
			response.sendRedirect("/api/auth/logout");
		
		} catch (Exception e) {
			logger.error("Error: {}", e);
		}
		
	}

}
