package com.example.demo.service.implementation;

import org.springframework.stereotype.Service;

import com.example.demo.model.Token;
import com.example.demo.repository.TokenRepository;
import com.example.demo.service.TokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final TokenRepository tokenRepository;

    @Override
    public Token getTokenById(Integer id) {
        
        Token token = tokenRepository.getById(id);

        return token;
    }

    @Override
    public void saveToken(Token token) {
        tokenRepository.save(token);       
    }
    
}
