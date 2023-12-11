package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/home")
public class HomeController {

	@GetMapping("/hello")
	public ResponseEntity<?> sayHello() {
		
		try {
			System.out.println("masuk sayHello");
			return new ResponseEntity<>("Selamat datang!", HttpStatus.OK);			
		} catch (Exception e) {
			System.out.println("masuk error");
			return new ResponseEntity<>("Access Denied", HttpStatus.FORBIDDEN);
		}
	}
}