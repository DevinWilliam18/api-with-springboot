package com.example.demo.controller;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.DemoApplication;
import com.example.demo.config.SecurityConfig;
import com.example.demo.dto.UserRegisterDto;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.service.implementation.JwtService;
import com.example.demo.service.implementation.RolelServiceImpl;
import com.example.demo.service.implementation.TokenServiceImpl;
import com.example.demo.service.implementation.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

//	@Autowired
//	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;

	@MockBean
	private RolelServiceImpl roleService;
	
	@MockBean
	private JwtService jwtService;
	
	@MockBean
	private UserDetailsService userDetailsService;

	@MockBean
	private AuthenticationManager authenticationManager;
	
	@MockBean
	private TokenServiceImpl tokenService;
//	
//	@MockBean
//	private ModelMapper modelMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private PasswordEncoder passwordEncoder;

//	@Autowired
//	private ApplicationContext context;
	
	private UserRegisterDto user;
	
	private User userModel;
	
	private Set<Role> roles;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
//	@BeforeAll
//	private void setupForAll() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//	}
	
	@BeforeEach
	public void setUpInitData() {
		
		List<Role> listOfRoles = new ArrayList<>();
		
		listOfRoles.add(Role.builder().name("ROLE_USER").build());
		
		roles = new HashSet<>(listOfRoles); 		
		
		user = UserRegisterDto.builder()
				.name("vins")
				.email("vins@gmail.com")
				.password("vins1234")
				.build();
		
		userModel = User.builder()
					.name("vins")
					.email("vins@gmail.com")
					.password("vins1234")
					.roles(roles)
					.build();
		
	}
	
	@Test
	void testRegisterWithValidInput_thenReturns200(){

			try {
				
				when(userService.findUserByEmail(user.getEmail())).thenReturn(false);
				
				when(userService.findUserByUsername(user.getName())).thenReturn(false);
				
				when(roleService.findRoleByName("ROLE_ADMIN")).thenReturn(new ArrayList<>(roles).get(0));
				
				when(passwordEncoder.encode(user.getPassword())).thenReturn(String.format("TEST-%s-TEST", user.getPassword()));
				
				when(userService.registerUser(any(User.class))).thenReturn(true);
				
				this.mockMvc.perform(post("/api/auth/register")
						.content(objectMapper.writeValueAsString(user))
						.contentType(MediaType.APPLICATION_JSON)
						.with(csrf()))
						.andExpect(status().isOk());
			} catch (JsonProcessingException e) {
				
			} catch (Exception e) {
				
			}
			


	}

	@Test
	void testRegisterWithExistingEmail_thenReturns400() {

		
		try {
			
			when(userService.findUserByEmail(user.getEmail())).thenReturn(true);
			
			this.mockMvc.perform(post("/api/auth/register")
					.content(objectMapper.writeValueAsString(user))
					.contentType(MediaType.APPLICATION_JSON)
					.with(csrf()))
					.andExpect(status().isBadRequest());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	@Test
	void testRegisterWithExistingUsername_thenReturns400() {

		try {
			
			when(userService.findUserByUsername(user.getName())).thenReturn(true);
			
			this.mockMvc.perform(post("/api/auth/register")
					.content(objectMapper.writeValueAsString(user))
					.contentType(MediaType.APPLICATION_JSON)
					.with(csrf()))
					.andExpect(status().isBadRequest());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}	
	
	@Test
	void testRegisterWithInvalidInput_thenReturns422() {
		
	}	
	
//	@Test
//	void testLoginWitValidUser_thenReturns200(){
//		when(null
//	}

	
}
