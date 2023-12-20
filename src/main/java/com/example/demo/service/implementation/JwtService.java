package com.example.demo.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.model.Token;
import com.example.demo.repository.TokenRepository;

import java.security.Key;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

	@Value("${app.security.jwt.secret-key}")
	private String jwtSecretKey;
	
	@Value("${app.security.jwt.expiration}")	
	private int jwtExpiration;
	
	private final TokenServiceImpl tokenServiceImpl;
	
	public String generateToken(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpiration))
				.signWith(key(), SignatureAlgorithm.HS256)
				.compact();
	}

	public String getUsernameFromJwtToken(String token) {
		
		try {
			return Jwts.parserBuilder().setSigningKey(key()).build()
					.parseClaimsJws(token).getBody().getSubject();
		} catch (MalformedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		}
		return null;
	}
	
	public boolean isTokenValid(String authToken, UserDetails uerDetails) {
		try {
			if (getUsernameFromJwtToken(authToken).equals(uerDetails.getUsername()) && !isExpired(authToken)) {
				return true;
			}
//			Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}
	
	public boolean isExpired(String authToken) {
		Token token =  tokenServiceImpl.getTokenByToken(authToken);
		if (!token.isExpired() && ! token.isRevoked()) {
			return false;
		}
		return true;
	}

	public String parseJwt(HttpServletRequest req) {
		
		try {
			String authHeader = req.getHeader("Authorization");
			if (authHeader != null || authHeader.startsWith("Bearer ")) {
				return authHeader.split(" ")[1].trim();
			}
			
		}catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		}catch (NullPointerException e) {
			logger.error("Null: {}", e.getMessage());
		}
		catch (Exception e) {
			logger.error("Error: {}", e.getMessage());
		}
		return null;
	}
	
	public Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
	}

}