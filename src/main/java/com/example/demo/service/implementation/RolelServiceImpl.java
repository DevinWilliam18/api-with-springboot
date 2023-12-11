package com.example.demo.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.RoleService;

@Service
public class RolelServiceImpl implements RoleService{

	private final RoleRepository roleRepository;
	
	@Autowired
	public RolelServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public boolean registerRole(Role role){
		try {
			roleRepository.save(role);
			return true;
		} catch (Exception e) {
			System.out.println("RolelServiceImpl: " + e);
			System.out.println("couldn't create the data");
		}
		return false;
	}

	@Override
	public Role findRoleByName(String name){
		
		try {
			Role role = roleRepository.findByName(name);
			
			if (role != null) {
				return role;
			}else {
				throw new Exception("can't create user from RolelServiceImpl");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Exception: " + e);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
//	@Override
//	public boolean registerRole(Role role) {
//		
//		roleRepository.save(role);
//		
//		return false;
//	}
//

	
	
}
