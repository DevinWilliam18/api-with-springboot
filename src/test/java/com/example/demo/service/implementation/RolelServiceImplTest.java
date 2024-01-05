package com.example.demo.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;


@ExtendWith(MockitoExtension.class)
public class RolelServiceImplTest {
	
	@Mock
	private RoleRepository roleRepository;
	
	@InjectMocks
	private RolelServiceImpl rolelServiceImpl;
	
	private Role role, roleReq, roleResp;
	
	@BeforeEach
	private void setUpInitData() {
		role = Role.builder().name("ROLE_ADMIN").build();
	}
	
    @Test
    void testFindRoleByName() {
    	when(roleRepository.findByName(role.getName())).thenReturn(role);
    	
    	roleResp = rolelServiceImpl.findRoleByName(role.getName());
    	
    	assertEquals(role.getName(), roleResp.getName());
    	
    	verify(roleRepository, times(1)).findByName(role.getName());
    	
    }

    @Test
    void testRegisterRole() {
    	roleReq = Role.builder().name("ROLE_USER").build();
    	
    	when(roleRepository.save(roleReq)).thenReturn(roleReq);
    	
    	boolean resp = rolelServiceImpl.registerRole(roleReq);
    	
    	assertEquals(true, resp);
    	
    	verify(roleRepository, times(1)).save(roleReq);
    }
}
