package com.example.demo.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.query.criteria.internal.predicate.ExistsPredicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.context.Theme;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtAuthenticationFilter;
import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.UserRegisterDto;
import com.example.demo.model.Role;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.service.implementation.JwtService;
import com.example.demo.service.implementation.RolelServiceImpl;
import com.example.demo.service.implementation.UserDetailServiceImpl;
import com.example.demo.service.implementation.UserServiceImpl;
import com.example.demo.service.implementation.TokenServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    
	private final AuthenticationManager authenticationManager;
	
	private final UserService userService;
	
	private final UserDetailsService userDetailsService;
    
    private final JwtService jwtService;
    
    private final RoleService rolelService;

    private final TokenService tokenService;
    
    private final ModelMapper modelMapper;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserController(AuthenticationManager authenticationManager, UserService userService,
			UserDetailsService userDetailsService, JwtService jwtService, RoleService rolelService,
			TokenService tokenService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {

		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
		this.rolelService = rolelService;
		this.tokenService = tokenService;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
     public ResponseEntity<?> loginUser(@RequestBody UserLoginDto userLoginDto) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtString = jwtService.generateToken(userLoginDto.getUsername());
			
			//get user's info
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			
			User user = userService.getUserByUsername(userDetails.getUsername());
			
			Token token = Token.builder()
					.token(jwtString)
					.revoked(false)
					.expired(false)
					.user(user)
					.build();
			
			tokenService.saveToken(token);
			
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("token", jwtString);
			resultMap.put("username", userDetails.getUsername());
			
			return new ResponseEntity<>(resultMap, HttpStatus.OK);
			
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>("incorrect password and username!", HttpStatus.UNAUTHORIZED);
		}catch (Exception e) {
			return new ResponseEntity<>("couldn't login", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto){
    	try {

//    		Check email if it already exists or not
    		if (userService.findUserByEmail(userRegisterDto.getEmail())) {
    			return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
			}
    		
//    		Check username if it already exists or not    		
    		if (userService.findUserByUsername(userRegisterDto.getName())) {
    			return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);				
			}
    		
    		
			User user = modelMapper.map(userRegisterDto, User.class);
			
			Role role = rolelService.findRoleByName("ROLE_ADMIN");

			//encoding password before save into database
			String password = passwordEncoder.encode(user.getPassword());
	
			user.setPassword(password);

			
			//save the entity into database
			user.setRoles(Collections.singleton(role));

			if (userService.registerUser(user)) {
				return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println("Register: " + e.getMessage());
		}
    	return new ResponseEntity<>("couldn't create user", HttpStatus.UNPROCESSABLE_ENTITY);

    }
    
    @GetMapping("/successful-logout")
    public ResponseEntity<?> greetings() {
    	return new ResponseEntity<>("Logout successfully", HttpStatus.OK);
	}
    
}