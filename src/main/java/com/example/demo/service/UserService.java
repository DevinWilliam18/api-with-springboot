package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserLoginDto;
import com.example.demo.model.User;


public interface UserService {
    
    boolean registerUser(User user);
 
    boolean findUserByUsername(String username);
    
    boolean findUserByEmail(String email);
    
    User getUserByUsername(String username);
    
}
