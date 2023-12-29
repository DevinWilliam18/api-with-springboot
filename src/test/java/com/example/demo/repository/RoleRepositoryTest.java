package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Role;

@DataJpaTest
public class RoleRepositoryTest {
	
	@Autowired
	private RoleRepository roleRepository;
	
	private Role role;
	
	@BeforeEach
	public void setupInitData() {
		role = Role
				.builder()
				.name("ROLE_ADMIN")
				.build();
	}
	
    @Test
    void testFindByName() {
    	roleRepository.save(role);
    	
    	Role roleResponse = roleRepository.findByName(role.getName());
    	
    	assertEquals(roleResponse.getName(), role.getName());
    	
    }
    
}
