package com.example.authservice.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class CustomPasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean match(String password, String encodedPassword) {
        bCryptPasswordEncoder.matches(password, encodedPassword);
        return true;
    }
}
