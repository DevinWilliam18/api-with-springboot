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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component; 	
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.implementation.JwtService;
import com.example.demo.service.implementation.UserDetailServiceImpl;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Component
@NoArgsConstructor(force = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtService jwtService;
	
	private final UserDetailServiceImpl userDetailServiceImpl;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	

	@Autowired
	public JwtAuthenticationFilter(JwtService jwtService, UserDetailServiceImpl userDetailServiceImpl) {
		this.jwtService = jwtService;
		this.userDetailServiceImpl = userDetailServiceImpl;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String jwt = parseJwt(request);
			System.out.println("jwt : " + jwt);
			System.out.println("jwtService.isTokenValid(jwt)" + jwtService.isTokenValid(jwt));
			if (jwt != null && jwtService.isTokenValid(jwt)) {
				/* get username from token */
				
				System.out.println("masuk if: " + jwt);
				
				String email = jwtService.getUsernameFromJwtToken(jwt);

				UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(email);
				 
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				System.out.println("masuk doFilter: " + auth);
				/* Set the authentication for the current context*/
				SecurityContextHolder.getContext().setAuthentication(auth);				
			}
		} catch (Exception e) {
			logger.error("Can't set user authentication: " + e);
		}
		filterChain.doFilter(request, response);
	}
	
	private String parseJwt(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return null;
		}
		return authHeader.substring(7);
	}
	
}
