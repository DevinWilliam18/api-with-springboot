package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Token;


@DataJpaTest
public class TokenRepositoryTest {

    @Autowired
	private TokenRepository tokenRepository;
	
	private Token token;
	
	@BeforeEach
	public void setUpInitData() {
		token = Token
				.builder()
				.token("hh123")
				.revoked(false)
				.expired(false)
				.build();
	}
	
    @Test
    void testFindTokenByToken() {
    	
    	tokenRepository.save(token);
    	
    	Token response = tokenRepository.findTokenByToken(token.getToken());
    	
    	assertEquals(response.getToken(), token.getToken());
    }
}
