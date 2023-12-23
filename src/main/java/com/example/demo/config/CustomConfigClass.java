package com.example.demo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.example.demo.repository.TokenRepository;
import com.example.demo.service.implementation.TokenServiceImpl;
import com.example.demo.model.Token;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
public class CustomConfigClass implements LogoutHandler{

	private static Logger logger = LoggerFactory.getLogger(CustomConfigClass.class);
	
	private final TokenServiceImpl tokenServiceImpl;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		try {
			final String authHeader = request.getHeader("Authorization");
			final String jwt = authHeader.split(" ")[1].trim();
			
			
		    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
		        return;
		    }
			
		    Token storedToken = tokenServiceImpl.getTokenByToken(jwt);
		    System.out.println("storedToken: " + storedToken);
		    if (storedToken != null) {
				storedToken.setExpired(true);
				storedToken.setRevoked(true);
				tokenServiceImpl.saveToken(storedToken);
				SecurityContextHolder.clearContext();
			}
		} catch (Exception e) {
			logger.error("Error: {}", e);
		}
		
	}

}
