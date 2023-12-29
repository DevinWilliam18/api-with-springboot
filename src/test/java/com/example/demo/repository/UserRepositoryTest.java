package com.example.demo.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.User;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setupInitData() {
        user = User
                .builder()
                .name("Vins William")
                .email("devinfcb42@gmail.com")
                .password("dvn_2023")
                .build();
        
    }

    @Test
    @DisplayName(value = "JUnit test for checking user by email")
    void testExistsByEmail() {
    	userRepository.save(user);
    	
    	assertTrue(userRepository.existsByEmail(user.getEmail()));
    }

    @Test
    @DisplayName(value = "JUnit test for checking user by name")
    void testExistsByName() {
    	userRepository.save(user);
    	
    	assertTrue(userRepository.existsByName(user.getName()));
    	
    }

    @Test
    @DisplayName(value = "JUnit test for retrieving user by name")
    void testFindByName() {
    	userRepository.save(user);
    	User response = userRepository.findByName(user.getName());
    	
    	assertEquals(response.getName(), user.getName());
    	
    }

    @Test
    @DisplayName(value = "jUnit test for saving user")
    void testSave() {
        //when
        User saveUser = userRepository.save(user);

        //then
        assertEquals("Vins William", saveUser.getName());

    }
}