package com.example.demo.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, String>  {
    
    User findByName(String username) throws DataAccessException;
    
    User save(User user) throws DataAccessException;
    
    boolean existsByName(String name);
    
    boolean existsByEmail(String username);
    
}