package com.example.demo.service;

import com.example.demo.model.Role;

public interface RoleService {
	
	boolean registerRole(Role role) throws Exception;
	
	Role findRoleByName(String name) throws IllegalArgumentException;
	
}
