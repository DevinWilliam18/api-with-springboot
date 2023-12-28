package com.example.demo.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void testExistsByEmail() {

    }

    @Test
    void testExistsByName() {

    }

    @Test
    void testFindByName() {

    }

    @Test
    @DisplayName(value = "jUnit test for saving employee")
    void testSave() {
        //when
        User saveUser = userRepository.save(user);

        //then
        assertEquals("Vins William", saveUser.getName());

    }
}