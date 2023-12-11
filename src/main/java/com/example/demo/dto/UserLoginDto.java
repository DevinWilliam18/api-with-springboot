package com.example.demo.dto;

import lombok.Data;

@Data
public class UserLoginDto {
    
    private String username;
    private String password;
    
	public UserLoginDto() {
	
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
    
}