package com.example.demo.service;

import com.example.demo.model.Token;

public interface TokenService {
    
    Token getTokenById(Integer id);

    void saveToken(Token token);

}
