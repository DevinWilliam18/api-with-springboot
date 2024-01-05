package com.example.demo.service.implementation;

import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.bytecode.internal.javassist.BulkAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.config.JwtAuthenticationFilter;
import com.example.demo.dto.UserLoginDto;
import com.example.demo.dto.UserRegisterDto;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.Setter;


@Service
public class UserDetailServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	    
    
    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User user;
		Set<GrantedAuthority> authorities;
		try {
			user = userRepository.findByName(username);
			
			if (user == null) {
			    throw new UsernameNotFoundException("can't found user");  
			}

			authorities = user.getRoles().stream().map((role) -> 
			new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		} catch (Exception e) {
			user = null;
			authorities = null;
			logger.error("Error: {} " ,e);
		}

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);

    }
}