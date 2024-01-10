package com.example.demo.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.demo.model.Role;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.service.implementation.TokenServiceImpl;

import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
 	
	@Mock
	private TokenServiceImpl tokenServiceImpl;
	
	@InjectMocks
	private JwtService jwtService;
	
	@Mock
	private HttpServletRequest httpServletRequest;
	
	private User user;

	private Token token;
	
	@BeforeEach
	private void name() {
		
		List<Role> listOfRoles = new ArrayList<>();
		
		listOfRoles.add(Role.builder().name("ROLE_USER").build());
		
		Set<Role> roles = new HashSet<>(listOfRoles); 		
		
		user = User.builder()
				.name("vins")
				.email("vins@gmail.com")
				.password("vins1234")
				.roles(roles)
				.build();

        // fil methods inside the JwtService class with a value for testing
        ReflectionTestUtils.setField(jwtService, "jwtSecretKey", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");

        // fil methods inside the JwtService class with a value for testing
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000);                
		
	}
	
    @Test
    void testGenerateToken() {

    	String accessToken = jwtService.generateToken(user.getName());

    	assertNotNull(accessToken);
    	
    }

    @Test
    void testGetUsernameFromJwtToken() {

        String accessToken = jwtService.generateToken(user.getName());

        String username = jwtService.getUsernameFromJwtToken(accessToken);

        assertEquals(user.getName(), username);
    }

    @Test
    void testIsExpired() {
    	
    	String generatedToken = jwtService.generateToken(user.getName());
    	
    	token = Token.builder().token(generatedToken).expired(true).revoked(false).build();
   
    	when(tokenServiceImpl.getTokenByToken(generatedToken)).thenReturn(token);
    	
    	boolean tokenIsExpired = jwtService.isExpired(generatedToken);
    	
    	assertEquals(true, tokenIsExpired);
    	
    	
    }

    @Test
    void testIsTokenValid() {
    	
    	String generatedToken = jwtService.generateToken(user.getName());
    	token = Token.builder().token(generatedToken).expired(false).revoked(false).build();
    	   
    	when(tokenServiceImpl.getTokenByToken(generatedToken)).thenReturn(token);
    	
    	boolean tokenIsValid = jwtService.isTokenValid(generatedToken, user.getName());
    	
    	assertEquals(true, tokenIsValid);
    	
    	
    }

    @Test
    void testKey() {
    	Key key = jwtService.key();
    	
    	assertNotNull(key);
    }

    @Test
    void testParseJwt() {
    	
    	when(httpServletRequest.getHeader("Authorization")).thenReturn("Bearer 123xxx");
    	
    	String parsedJwt = jwtService.parseJwt(httpServletRequest);
    	
    	assertNotNull(parsedJwt);
    	
    }
}
