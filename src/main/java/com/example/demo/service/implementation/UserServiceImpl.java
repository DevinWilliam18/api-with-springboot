package com.example.demo.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;


@Service
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }	
	
	@Override
	public boolean registerUser(User user) {
		
		try {
			User userUpdated = userRepository.save(user);
			if (userUpdated != null) {
				return true;
			}else {
				throw new Exception("Can't create user! from DetailService class");
			}
		}catch(DataAccessException dae){
			System.out.println("DataAccessException: " + dae);
			dae.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("General Exception: " + e);
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean findUserByUsername(String username) {
		if (userRepository.existsByName(username)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean findUserByEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			return true;
		}
		return false;
	}

	@Override
	public User getUserByUsername(String username) {
		try {
			User user = userRepository.findByName(username);
			if (user != null) {
				return user;
			}
		} catch (Exception e) {
			System.out.println("error: " + e);
		}
		return null;
	}

}
