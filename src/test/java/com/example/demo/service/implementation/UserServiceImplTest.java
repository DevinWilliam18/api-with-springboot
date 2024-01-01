package com.example.demo.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.config.CustomConfigClass;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);


    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User user;

    @BeforeEach
    public void setupInitData(){
        user = User
                .builder()
                .name("Vins")
                .email("devinfcb42@gmail.com")
                .password("2212")
                .build();
    }

    

    @Test
    void testFindUserByEmail() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertEquals(true, userServiceImpl.findUserByEmail(user.getEmail()));
    }

    @Test
    void testFindUserByUsername() {
        when(userRepository.existsByName(user.getName())).thenReturn(true);

        assertEquals(true, userServiceImpl.findUserByUsername(user.getName()));

    }

    @Test
    void testGetUserByUsername() {
        when(userRepository.findByName(user.getName())).thenReturn(user);

        assertEquals(user, userServiceImpl.getUserByUsername(user.getName()));
    }

    @Test
    void testRegisterUser() {
        User savedUser = User
                .builder()
                .name("gogo")
                .email("gogo42@gmail.com")
                .password("2212")
                .build();

        when(userRepository.save(savedUser))
        
         
    }
}
