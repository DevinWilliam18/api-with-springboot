package com.example.demo.service;

import com.example.demo.model.Token;

public interface TokenService {
    
    Token getTokenByToken(String token);

    void saveToken(Token token);

}
