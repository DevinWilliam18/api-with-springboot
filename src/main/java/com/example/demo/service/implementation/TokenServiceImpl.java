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
    public Token getTokenByToken(String token) {
        
        Token token_res = tokenRepository.findTokenByToken(token);
        return token_res;
    }

    @Override
    public void saveToken(Token token) {
        tokenRepository.save(token);       
    }
    
}
