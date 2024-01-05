package com.example.demo.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceImplTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserDetailServiceImpl userDetailServiceImpl;
	
	private User user;
	
	@BeforeEach
	private void setupInitData() {
		
		List<Role> listOfRoles = new ArrayList<>();
		
		listOfRoles.add(Role.builder().name("ROLE_USER").build());
		
		Set<Role> roles = new HashSet<>(listOfRoles);
		
        user = User
                .builder()
                .name("Vins")
                .email("devinfcb42@gmail.com")
                .password("2212")
                .roles(roles)
                .build();
	}
	
    @Test
    void testLoadUserByUsername() {
    	
    	when(userRepository.findByName(user.getName())).thenReturn(user);
    	
    	
    	UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(user.getName());
    	
    	assertNotNull(userDetails);
    	assertEquals(userDetails.getUsername(), user.getName());
    }
}
